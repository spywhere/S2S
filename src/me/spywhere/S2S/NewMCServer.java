package me.spywhere.S2S;

public class NewMCServer {
	private String serverIP;
	private int serverPort;
	
	public NewMCServer(String serverIP,int serverPort){
		this.serverIP=serverIP;
		this.serverPort=serverPort;
	}
	
	public String getServerIP(){
		return this.serverIP;
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
}
