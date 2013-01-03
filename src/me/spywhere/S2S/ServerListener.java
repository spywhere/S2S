package me.spywhere.S2S;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ServerListener implements Runnable {
	//For server
	boolean isRunning=false;
	int taskID=-1;
	S2S plugin;
	
	public ServerListener(S2S instance) {
		plugin=instance;
	}
	
	public void start() {
		if(isRunning){ return; }
		isRunning=true;
		BukkitTask task=Bukkit.getScheduler().runTaskAsynchronously(plugin, this);
		taskID=task.getTaskId();
	}
	
	public void stop() {
		if(!isRunning){ return; }
		isRunning=false;
		long checkTime = System.currentTimeMillis()+plugin.disconnectTimeout;
		while(checkTime>System.currentTimeMillis()){
			if(!Bukkit.getScheduler().isCurrentlyRunning(taskID)){
				break;
			}
		}
		Bukkit.getScheduler().cancelTask(taskID);
	}
	
	@Override
	public void run() {
		plugin.log.info("["+plugin.getDescription().getName()+"] Starting server...");
		ServerSocket serverSocket=null;
		try{
			serverSocket=new ServerSocket(plugin.listenPort);
			serverSocket.setSoTimeout(plugin.connectionTimeout);
			plugin.log.info("["+plugin.getDescription().getName()+"] Server started.");
		}catch (IOException e){
			e.printStackTrace();
		}
		if(serverSocket!=null){
			Socket socket = null;
			while (isRunning){
				try{
					socket = serverSocket.accept();
					if(plugin.showIncoming){
						plugin.log.info("["+plugin.getDescription().getName()+"] Incoming packet from ["+socket.getLocalAddress().getHostAddress()+":"+socket.getLocalPort()+"]...");
					}
					
					ObjectInputStream input=new ObjectInputStream(socket.getInputStream());
					Object object = input.readObject();
					if(object instanceof Packet){
						Packet respond = (Packet) object;
						if(respond.getPacketID()==PacketID.Ping){
							Packet packet = new Packet(PacketID.Pong,socket.getLocalAddress().getHostAddress(),plugin.getDescription().getName(),plugin.getDescription().getVersion(),"Welcome to the server!");
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							output.flush();
							output.writeObject(packet);
							output.close();
						}else{
							//Write respond
							Packet packet = new Packet(PacketID.Pong,socket.getLocalAddress().getHostAddress(),respond.getPluginName(),respond.getPluginVersion(),null);
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							output.flush();
							output.writeObject(packet);
							output.close();
							
							if(plugin.connectorList.containsKey(respond.getPluginName().toLowerCase())){
								PacketListener listener = plugin.connectorList.get(respond.getPluginName().toLowerCase()).getPacketListener();
								if(plugin.hasPermission(plugin.parsePluginName(respond.getPluginName()), PluginPermissions.ReadData.getID())){
									listener.onPacketReceived(respond);
								}else{
									listener.onPacketFailed(packet, new PacketException("No read permission!"));
								}
							}
						}
					}else{
						//Invalid packet
					}
					socket.close();
				}catch (SocketException e){
					e.printStackTrace();
				}catch (SocketTimeoutException e){
					
				}catch (IOException e){
					e.printStackTrace();
				}catch (ClassNotFoundException e){
					//WTF Just happened?
					e.printStackTrace();
				}
			}
			try{
				plugin.log.info("["+plugin.getDescription().getName()+"] Disconnecting...");
				serverSocket.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		plugin.log.info("["+plugin.getDescription().getName()+"] Disconnected.");
	}
}
