package me.spywhere.S2S;

public enum PacketID {
	Invalid(0),Ping(1),Pong(2),Internal(3),Data(4);
	private int id;
	private PacketID(int id){
		this.id=id;
	}
	
	public int getID(){
		return id;
	}
}
