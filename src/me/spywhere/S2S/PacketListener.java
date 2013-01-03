package me.spywhere.S2S;

// TODO: Auto-generated Javadoc
/**
 * PacketListener Interface.
 */
public interface PacketListener {
	
	/**
	 * On packet received.
	 *
	 * @param packet Packet
	 */
	public void onPacketReceived(Packet packet);
	
	/**
	 * On packet sent.
	 *
	 * @param packet Packet
	 * @param respond Respond packet
	 */
	public void onPacketSent(Packet packet,Packet respond);
	
	/**
	 * On packet failed.
	 *
	 * @param packet Packet
	 * @param pe PacketException
	 */
	public void onPacketFailed(Packet packet, PacketException pe);
}
