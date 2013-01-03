package me.spywhere.S2S;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MCServer extends NewMCServer implements Runnable{
	private class EmptyPacketListener implements PacketListener{
		@Override
		public void onPacketReceived(Packet packet) {}
		
		@Override
		public void onPacketSent(Packet packet,Packet respond) {}
		
		@Override
		public void onPacketFailed(Packet packet, PacketException pe) {}
	}
	
	private Plugin plugin;
	private Object object=null;
	private PacketListener listener=new EmptyPacketListener();
	private int timeout=5000;
	private boolean isSending=false;
	
	protected MCServer(String serverIP,int serverPort){
		super(serverIP,serverPort);
	}
	
	public boolean isSending(){
		return isSending;
	}
	
	public boolean sendData(ServerConnector sender,Object data,int timeout){
		if(!sender.hasPermission(PluginPermissions.SendData)){return false;}
		if(isSending){return false;}
		this.isSending=true;
		this.plugin=sender.getPlugin();
		this.object=data;
		this.listener=sender.getPacketListener();
		this.timeout=timeout;
		Bukkit.getScheduler().runTaskAsynchronously(plugin, this);
		return true;
	}
	
	public void sendData(ServerConnector sender,Object data){
		sendData(sender,data,timeout);
	}
	
	@Override
	public void run() {
		if(isSending){
			long currentTime = System.currentTimeMillis()+timeout;
			Packet packet = new Packet(PacketID.Data,this.getServerIP(),plugin.getDescription().getName(),plugin.getDescription().getVersion(),object);
			try{
				Socket socket = new Socket(this.getServerIP(),this.getServerPort());
				socket.setSoTimeout(50);
				Packet response=null;
				while(response==null){
					try{
						if(currentTime<System.currentTimeMillis()){
							listener.onPacketFailed(packet, new PacketException("Packet timeout!"));
							break;
						}
						ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
						output.flush();
						output.writeObject(packet);
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						Object object = input.readObject();
						if(object instanceof Packet){
							Packet respond = (Packet) object;
							if(respond.getPacketID()==PacketID.Pong){
								listener.onPacketSent(packet,respond);
								response=respond;
							}
						}
						output.close();
					}catch (SocketTimeoutException e){
						//Timeout
					}catch (SocketException e){
						//Socket Error:
						listener.onPacketFailed(packet, new PacketException("Socket error!",e));
					}catch (IOException e){
						//IO Error:
						listener.onPacketFailed(packet, new PacketException("IO error!",e));
					}catch (ClassNotFoundException e){
						//Invalid packet
						listener.onPacketFailed(packet, new PacketException("Invalid packet!",e));
					}
				}
			}catch (UnknownHostException e){
				listener.onPacketFailed(packet, new PacketException("Unknown host!",e));
			}catch (SocketException e){
				e.printStackTrace();
			}catch (IOException e){
				e.printStackTrace();
			}
			isSending=false;
		}
	}
}
