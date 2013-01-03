package me.spywhere.S2S;

import java.util.Set;

import org.bukkit.plugin.Plugin;

public class ServerConnector {
	private class EmptyPacketListener implements PacketListener {
		@Override
		public void onPacketReceived(Packet packet) {
		}
		
		@Override
		public void onPacketSent(Packet packet, Packet respond) {
		}
		
		@Override
		public void onPacketFailed(Packet packet, PacketException pe) {
		}
	}
	
	private S2S s2s;
	private Plugin plugin;
	private PacketListener listener=new EmptyPacketListener();
	
	protected ServerConnector(S2S s2s,Plugin plugin, PacketListener listener) {
		this.s2s=s2s;
		this.plugin=plugin;
		this.listener=listener;
	}
	
	public boolean addServer(NewMCServer server, String name) {
		if(!hasPermission(PluginPermissions.AddServer)){return false;}
		MCServer mcserver = new MCServer(server.getServerIP(),server.getServerPort());
		s2s.serverList.put(name.toLowerCase(), mcserver);
		return false;
	}
	
	public Set<String> getServers() {
		return s2s.serverList.keySet();
	}
	
	public boolean hasServer(String name){
		return s2s.serverList.containsKey(name.toLowerCase());
	}
	
	public boolean removeServer(String name) {
		if(!hasPermission(PluginPermissions.RemoveServer)){return false;}
		return false;
	}
	
	public MCServer getServer(String name) {
		if(hasServer(name)){
			return s2s.serverList.get(name.toLowerCase());
		}
		return null;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public PacketListener getPacketListener(){
		return listener;
	}
	
	public boolean hasPermission(PluginPermissions permission) {
		return s2s.hasPermission(plugin.getDescription().getName(), permission.getID());
	}
}
