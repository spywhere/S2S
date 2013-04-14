package me.spywhere.S2S;

/**
 * NewMCServer Class.
 */
public class NewMCServer {
	/** Server IP. */
	private String serverIP;
	/** Server port. */
	private int serverPort;
	
	/**
	 * Instantiates a new mc server.
	 * 
	 * @param serverIP
	 *            Server IP
	 * @param serverPort
	 *            Server port
	 */
	public NewMCServer(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	/**
	 * Get server IP.
	 * 
	 * @return Server ip
	 */
	public String getServerIP() {
		return this.serverIP;
	}
	
	/**
	 * Get server port.
	 * 
	 * @return Server port
	 */
	public int getServerPort() {
		return this.serverPort;
	}
}
