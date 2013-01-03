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

// TODO: Auto-generated Javadoc
/**
 * MCServer Class.
 */
public class MCServer extends NewMCServer implements Runnable{
	
	/**
	 * EmptyPacketListener class.
	 *
	 * @see PacketListener
	 */
	private class EmptyPacketListener implements PacketListener{
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketReceived(me.spywhere.S2S.Packet)
		 */
		@Override
		public void onPacketReceived(Packet packet) {}
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketSent(me.spywhere.S2S.Packet, me.spywhere.S2S.Packet)
		 */
		@Override
		public void onPacketSent(Packet packet,Packet respond) {}
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketFailed(me.spywhere.S2S.Packet, me.spywhere.S2S.PacketException)
		 */
		@Override
		public void onPacketFailed(Packet packet, PacketException pe) {}
	}
	
	/** Plugin. */
	private Plugin plugin;
	
	/** Object. */
	private Object object=null;
	
	/** Packet Listener. */
	private PacketListener listener=new EmptyPacketListener();
	
	/** Timeout. */
	private int timeout=5000;
	
	/** isSending. */
	private boolean isSending=false;
	
	/**
	 * Instantiates a new server.
	 *
	 * @param serverIP Server IP
	 * @param serverPort Server port
	 */
	protected MCServer(String serverIP,int serverPort){
		super(serverIP,serverPort);
	}
	
	/**
	 * Checks if data is sending.
	 *
	 * @return true, if data is sending
	 */
	public boolean isSending(){
		return isSending;
	}
	
	/**
	 * Send data to this server.
	 *
	 * @param sender Server Connector
	 * @param data Data to send
	 * @param timeout Maximum time to send
	 * @return true, if successful
	 */
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
	
	/**
	 * Send data to this server.
	 *
	 * @param sender Server Connector
	 * @param data Data to send
	 * @return true, if successful
	 */
	public boolean sendData(ServerConnector sender,Object data){
		return sendData(sender,data,timeout);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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
