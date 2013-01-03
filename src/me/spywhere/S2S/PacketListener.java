package me.spywhere.S2S;

public interface PacketListener {
	public void onPacketReceived(Packet packet);
	public void onPacketSent(Packet packet,Packet respond);
	public void onPacketFailed(Packet packet, PacketException pe);
}
