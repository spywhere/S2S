package me.spywhere.S2S;

import java.io.Serializable;

public class Packet implements Serializable{
	private static final long serialVersionUID=1L;
	private PacketID packetId=PacketID.Invalid;
	private String serverIP="Unknown";
	private String pluginSent="Unknown";
	private String pluginVersion="0.0";
	private Object data=null;
	
	public Packet(PacketID packetId,String serverIP,String pluginSent,String pluginVersion,Object data){
		this.packetId=packetId;
		this.serverIP=serverIP;
		this.pluginSent=pluginSent;
		this.pluginVersion=pluginVersion;
		this.data=data;
	}
	
	public PacketID getPacketID(){
		return packetId;
	}
	
	public String getServerIP(){
		return serverIP;
	}
	
	public String getPluginName(){
		return pluginSent;
	}
	
	public String getPluginVersion(){
		return pluginVersion;
	}
	
	public Object getObject(){
		return data;
	}
	
	public boolean hasObject(){
		return data!=null;
	}
}
