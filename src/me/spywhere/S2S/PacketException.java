package me.spywhere.S2S;

public class PacketException extends Exception{
	private static final long serialVersionUID=1L;
	
	public PacketException(String msg){
		super(msg);
	}
	public PacketException(Throwable thrown){
		super(thrown);
	}
	public PacketException(String msg,Throwable thrown){
		super(msg,thrown);
	}
}
