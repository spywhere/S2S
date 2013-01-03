package me.spywhere.S2S;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * S2S Class.
 */
public class S2S extends JavaPlugin{
	
	/** Console logger. */
	protected Logger log=Logger.getLogger("Minecraft");
	
	/** Server listener. */
	protected ServerListener serverListener = new ServerListener(this);
	
	/** Connection timeout. */
	protected int connectionTimeout=50;
	
	/** Disconnect timeout. */
	protected int disconnectTimeout=5000;
	
	/** Listening port. */
	protected int listenPort=1234;
	
	/** Show incoming. */
	protected boolean showIncoming=true;
	
	/** Default perm. */
	protected String defaultPerm="nnyy";
	
	/** Server list. */
	protected HashMap<String,MCServer> serverList = new HashMap<String,MCServer>();
	
	/** Permission list. */
	protected HashMap<String,String> permissionList = new HashMap<String,String>();
	
	/** Connector list. */
	protected HashMap<String,ServerConnector> connectorList = new HashMap<String,ServerConnector>();
	
	/**
	 * Parses the plugin name.
	 *
	 * @param str Plugin name
	 * @return Parsed plugin name
	 */
	protected String parsePluginName(String str){
		return str.toLowerCase().replaceAll("-","--").replaceAll("_","__").replaceAll(":", "-").replaceAll("\\.", "_");
	}
	
	/**
	 * Checks for permission.
	 *
	 * @param plugin Plugin name
	 * @param id Permission ID
	 * @return true, if allowed
	 */
	protected boolean hasPermission(String plugin,int id){
		if(permissionList.containsKey(parsePluginName(plugin))){
			return permissionList.get(parsePluginName(plugin)).charAt(id)=='y';
		}
		return defaultPerm.charAt(id)=='y';
	}
	
	/**
	 * Request Server Connector.
	 *
	 * @param plugin Plugin
	 * @param listener Packet Listener
	 * @return Server Connector
	 */
	public ServerConnector requestSender(Plugin plugin,PacketListener listener){
		if(!permissionList.containsKey(parsePluginName(plugin.getDescription().getName()))){
			permissionList.put(parsePluginName(plugin.getDescription().getName()), this.defaultPerm);
		}
		ServerConnector sc = new ServerConnector(this,plugin,listener);
		connectorList.put(plugin.getDescription().getName().toLowerCase(), sc);
		return sc;
	}
	
	/**
	 * Save/Load configuration file
	 *
	 * @param save Save or not
	 */
	private void configSaveLoad(final boolean save) {
		try{
			final YMLIO yml=new YMLIO(new File(this.getDataFolder().toString(), "config.yml"));
			yml.setForceSave(save);
			yml.setHeader("Permissions\n[1][2][3][4]\n[1] = Add server\n[2] = Remove server\n[3] = Send data\n[4] = Read data\n\nExample: nnyy\nAllow send/read data but disallow add/remove server");
			this.listenPort=yml.get("S2S.ListenPort", this.listenPort);
			this.connectionTimeout=yml.get("S2S.ConnectionTime", this.connectionTimeout);
			this.disconnectTimeout=yml.get("S2S.DisconnectTime", this.disconnectTimeout);
			this.showIncoming=yml.get("S2S.ShowIncomingPacket", this.showIncoming);
			this.defaultPerm=yml.get("S2S.DefaultPermissions", this.defaultPerm);
			this.defaultPerm+="nnnn";
			String dperm="";
			for(int i=0;i<4;i++){
				if(defaultPerm.toLowerCase().charAt(i)=='y'){
					dperm+="y";
				}else{
					dperm+="n";
				}
			}
			this.defaultPerm=dperm;
			if(!save){
				List<String> servers = yml.getAsPathList("S2S.Servers");
				for(String path:servers){
					if(yml.contains("S2S.Servers."+path+".IP")&&yml.contains("S2S.Servers."+path+".Port")){
						String ip=yml.get("S2S.Servers."+path+".IP", "localhost");
						int port=yml.get("S2S.Servers."+path+".Port", this.listenPort);
						MCServer server = new MCServer(ip,port);
						serverList.put(path.toLowerCase(), server);
					}
				}
				List<String> plugins = yml.getAsPathList("S2S.Plugins");
				for(String path:plugins){
					String perm=yml.get("S2S.Plugins."+path, this.defaultPerm);
					permissionList.put(path.toLowerCase(), perm);
				}
			}else{
				yml.set("S2S.Servers", null);
				yml.set("S2S.Plugins", null);
				
				for(String key:serverList.keySet()){
					MCServer server = serverList.get(key);
					yml.set("S2S.Servers."+key.toLowerCase()+".IP", server.getServerIP());
					yml.set("S2S.Servers."+key.toLowerCase()+".Port", server.getServerPort());
				}
				
				for(String key:permissionList.keySet()){
					yml.set("S2S.Plugins."+key.toLowerCase(), permissionList.get(key));
				}
			}
			yml.save();
		}catch (final FileNotFoundException e){
			this.log.info("Error occurred while save/load config file:");
			e.printStackTrace();
		}catch (final IOException e){
			this.log.info("Error occurred while save/load config file:");
			e.printStackTrace();
		}catch (final InvalidConfigurationException e){
			this.log.info("Error occurred while save/load config file:");
			e.printStackTrace();
		}
	}
	
	/**
	 * Notify all ops on server.
	 *
	 * @param message Message
	 */
	protected void notifyOps(String message){
		for(Player player:this.getServer().getOnlinePlayers()){
			if(player.isOp()){
				player.sendMessage(message);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		this.configSaveLoad(false);
		serverListener.start();
		this.log.info("["+this.getDescription().getName()+"] v"+this.getDescription().getVersion()+" successfully enabled.");
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		serverListener.stop();
		this.configSaveLoad(true);
		this.log.info("["+this.getDescription().getName()+"] v"+this.getDescription().getVersion()+" successfully disabled.");
	}
	
	/**
	 * Parses the port.
	 *
	 * @param str Port (as string)
	 * @return Port (as number)
	 */
	private int parsePort(String str){
		try{
			return Integer.parseInt(str);
		}catch (final Exception e){}
		return listenPort;
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
		if(args.length>=1){
			if(args[0].equalsIgnoreCase("reload")){
				this.configSaveLoad(false);
				sender.sendMessage("S2S reloaded.");
				return true;
			}
			
			//s2s server add Serete-Craft 127.0.0.1 1234
			if(args[0].equalsIgnoreCase("server")){
				if(args.length>=3){
					if(args[1].equalsIgnoreCase("remove")){
						if(serverList.containsKey(args[2].toLowerCase())){
							serverList.remove(args[2].toLowerCase());
							sender.sendMessage("Server removed.");
						}else{
							sender.sendMessage("Server not found.");
						}
					}
					if(args.length>=5){
						if(args[1].equalsIgnoreCase("add")){
							if(!serverList.containsKey(args[2].toLowerCase())){
								MCServer server = new MCServer(args[3],parsePort(args[4]));
								serverList.put(args[2].toLowerCase(), server);
								sender.sendMessage("Server "+args[2].toLowerCase()+" added.");
							}else{
								sender.sendMessage("Server already exists.");
							}
						}
					}
				}else{
					for(String key:serverList.keySet()){
						MCServer server = serverList.get(key);
						sender.sendMessage(key+" "+server.getServerIP()+":"+server.getServerPort());
					}
				}
				return true;
			}
			
			if(args[0].equalsIgnoreCase("plugin")){
				if(args.length>=2){
					if(args.length>=3){
						if(args[1].equalsIgnoreCase("remove")){
							if(permissionList.containsKey(args[2].toLowerCase())){
								permissionList.remove(args[2].toLowerCase());
								sender.sendMessage("Plugin permissions removed.");
							}else{
								sender.sendMessage("Plugin permissions not found.");
							}
							return true;
						}
						if(args[2].length()==4){
							String perm="";
							args[2]+="nnnn";
							for(int i=0;i<4;i++){
								if(args[2].toLowerCase().charAt(i)=='y'){
									perm+="y";
								}else{
									perm+="n";
								}
							}
							permissionList.put(args[1].toLowerCase(), perm);
							sender.sendMessage("Permissions for "+args[1].toLowerCase()+" added.");
						}else{
							sender.sendMessage("Invalid permission.");
						}
					}
				}else{
					for(String key:permissionList.keySet()){
						sender.sendMessage(key+" = "+permissionList.get(key));
					}
				}
				return true;
			}
			
			if(args[0].equalsIgnoreCase("ping")){
				String serverIP="127.0.0.1";
				int port=this.listenPort;
				if(args.length>=2){
					if(serverList.containsKey(args[1].toLowerCase())){
						MCServer server = serverList.get(args[1].toLowerCase());
						serverIP=server.getServerIP();
						port=server.getServerPort();
					}else{
						sender.sendMessage("Server not found.");
						return true;
					}
				}
				try{
					long currentTime = System.currentTimeMillis();
					sender.sendMessage("Ping to "+serverIP+"!");
					Socket socket = new Socket(serverIP,port);
					socket.setSoTimeout(connectionTimeout);
					Packet response=null;
					Packet packet = new Packet(PacketID.Ping,socket.getLocalAddress().getHostAddress(),this.getDescription().getName(),this.getDescription().getVersion(),null);
					while(response==null){
						try{
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							output.writeObject(packet);
							ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
							Object object = input.readObject();
							if(object instanceof Packet){
								Packet respond = (Packet) object;
								if(respond.getPacketID()==PacketID.Pong){
									sender.sendMessage("Pong from "+respond.getServerIP()+"! ["+(System.currentTimeMillis()-currentTime)+"ms]");
									response=respond;
								}
							}
							output.close();
						}catch (SocketTimeoutException e){
							//Timeout
						}catch (SocketException e){
							sender.sendMessage("Socket error: "+e.getMessage());
						}catch (IOException e){
							sender.sendMessage("IO error: "+e.getMessage());
						}catch (ClassNotFoundException e){
							sender.sendMessage("Invalid packet!");
						}
					}
				}catch (UnknownHostException e){
					sender.sendMessage("Unknown host: "+serverIP);
				}catch (SocketException e){
					sender.sendMessage("Socket error: "+e.getMessage());
				}catch (IOException e){
					sender.sendMessage("IO error: "+e.getMessage());
				}
				return true;
			}
		}
		return false;
	}
}