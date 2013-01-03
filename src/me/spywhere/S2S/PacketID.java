package me.spywhere.S2S;

// TODO: Auto-generated Javadoc
/**
 * PacketID Enumerator.
 */
public enum PacketID {
	
	/** Invalid. */
	Invalid(0),
	/** Ping. */
	Ping(1),
	/** Pong. */
	Pong(2),
	/** Internal. */
	Internal(3),
	/** Data. */
	Data(4);
	
	/** Packet ID. */
	private int id;
	
	/**
	 * Instantiates a new packet ID.
	 *
	 * @param id Packet ID
	 */
	private PacketID(int id){
		this.id=id;
	}
	
	/**
	 * Get packet ID.
	 *
	 * @return Packet ID
	 */
	public int getID(){
		return id;
	}
}
