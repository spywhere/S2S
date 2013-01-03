package me.spywhere.S2S;

import java.util.Set;

import org.bukkit.plugin.Plugin;

// TODO: Auto-generated Javadoc
/**
 * ServerConnector Class.
 */
public class ServerConnector {
	
	/**
	 * EmptyPacketListener class.
	 *
	 * @see PacketListener
	 */
	private class EmptyPacketListener implements PacketListener {
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketReceived(me.spywhere.S2S.Packet)
		 */
		@Override
		public void onPacketReceived(Packet packet) {
		}
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketSent(me.spywhere.S2S.Packet, me.spywhere.S2S.Packet)
		 */
		@Override
		public void onPacketSent(Packet packet, Packet respond) {
		}
		
		/* (non-Javadoc)
		 * @see me.spywhere.S2S.PacketListener#onPacketFailed(me.spywhere.S2S.Packet, me.spywhere.S2S.PacketException)
		 */
		@Override
		public void onPacketFailed(Packet packet, PacketException pe) {
		}
	}
	
	/** S2S plugin. */
	private S2S s2s;
	
	/** Handle plugin. */
	private Plugin plugin;
	
	/** Packet listener. */
	private PacketListener listener=new EmptyPacketListener();
	
	/**
	 * Instantiates a new server connector.
	 *
	 * @param s2s S2S plugin
	 * @param plugin Handle plugin
	 * @param listener Packet listener
	 */
	protected ServerConnector(S2S s2s,Plugin plugin, PacketListener listener) {
		this.s2s=s2s;
		this.plugin=plugin;
		this.listener=listener;
	}
	
	/**
	 * Add new server.
	 *
	 * @param server Server
	 * @param name Name of server
	 * @return true, if successful
	 */
	public boolean addServer(NewMCServer server, String name) {
		if(!hasPermission(PluginPermissions.AddServer)){return false;}
		MCServer mcserver = new MCServer(server.getServerIP(),server.getServerPort());
		s2s.serverList.put(name.toLowerCase(), mcserver);
		return true;
	}
	
	/**
	 * Get servers.
	 *
	 * @return Set of server name
	 */
	public Set<String> getServers() {
		return s2s.serverList.keySet();
	}
	
	/**
	 * Checks for server.
	 *
	 * @param name Server name
	 * @return true, if exists
	 */
	public boolean hasServer(String name){
		return s2s.serverList.containsKey(name.toLowerCase());
	}
	
	/**
	 * Remove existing server.
	 *
	 * @param name Server name
	 * @return true, if successful
	 */
	public boolean removeServer(String name) {
		if(!hasPermission(PluginPermissions.RemoveServer)){return false;}
		return true;
	}
	
	/**
	 * Get server.
	 *
	 * @param name Server name
	 * @return Server, null if not exists
	 */
	public MCServer getServer(String name) {
		if(hasServer(name)){
			return s2s.serverList.get(name.toLowerCase());
		}
		return null;
	}
	
	/**
	 * Get handle plugin.
	 *
	 * @return Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Get packet listener.
	 *
	 * @return Packet listener
	 */
	public PacketListener getPacketListener(){
		return listener;
	}
	
	/**
	 * Checks for permission.
	 *
	 * @param permission Permission
	 * @return true, if allowed
	 */
	public boolean hasPermission(PluginPermissions permission) {
		return s2s.hasPermission(plugin.getDescription().getName(), permission.getID());
	}
}
