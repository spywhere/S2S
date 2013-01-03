package me.spywhere.S2S;

// TODO: Auto-generated Javadoc
/**
 * PacketException Class.
 */
public class PacketException extends Exception{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID=1L;
	
	/**
	 * Instantiates a new packet exception.
	 *
	 * @param msg Message
	 */
	public PacketException(String msg){
		super(msg);
	}
	
	/**
	 * Instantiates a new packet exception.
	 *
	 * @param thrown Throw
	 */
	public PacketException(Throwable thrown){
		super(thrown);
	}
	
	/**
	 * Instantiates a new packet exception.
	 *
	 * @param msg Message
	 * @param thrown Thrown
	 */
	public PacketException(String msg,Throwable thrown){
		super(msg,thrown);
	}
}
