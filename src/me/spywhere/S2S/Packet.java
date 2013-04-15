package me.spywhere.S2S;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Packet Class.
 */
public class Packet implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** Packet ID. */
	private PacketID packetId = PacketID.Invalid;
	/** Server IP. */
	private String serverIP = "Unknown";
	/** Server Port. */
	private int serverPort = 0;
	/** Name of plugin send this packet. */
	private String pluginSent = "Unknown";
	/** Version of plugin send this packet. */
	private String pluginVersion = "0.0";
	/** Data. */
	private Object data = null;
	
	/**
	 * Instantiates a new packet.
	 * 
	 * @param packetId
	 *            Packet ID
	 * @param serverIP
	 *            Server IP
	 * @param serverPort
	 *            Server Port
	 * @param pluginSent
	 *            Name of plugin send this packet
	 * @param pluginVersion
	 *            Version of plugin send this packet
	 * @param data
	 *            Data
	 */
	protected Packet(PacketID packetId, String serverIP, int serverPort, String pluginSent, String pluginVersion, Object data) {
		this.packetId = packetId;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.pluginSent = pluginSent;
		this.pluginVersion = pluginVersion;
		this.data = data;
	}
	
	/**
	 * Get packet ID.
	 * 
	 * @return Packet ID
	 */
	public PacketID getPacketID() {
		return packetId;
	}
	
	/**
	 * Get server IP.
	 * 
	 * @return Server IP
	 */
	public String getServerIP() {
		return serverIP;
	}
	
	/**
	 * Get server port.
	 * 
	 * @return Server Port
	 */
	public int getServerPort() {
		return serverPort;
	}
	
	/**
	 * Get plugin name.
	 * 
	 * @return Plugin name
	 */
	public String getPluginName() {
		return pluginSent;
	}
	
	/**
	 * Get plugin version.
	 * 
	 * @return Plugin version
	 */
	public String getPluginVersion() {
		return pluginVersion;
	}
	
	/**
	 * Get data.
	 * 
	 * @return Data
	 */
	public Object getObject() {
		return data;
	}
	
	/**
	 * Checks for data exists.
	 * 
	 * @return true, if data is exists
	 */
	public boolean hasObject() {
		return data != null;
	}
}
