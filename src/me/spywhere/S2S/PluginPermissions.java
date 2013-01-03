package me.spywhere.S2S;

// TODO: Auto-generated Javadoc
/**
 * PluginPermissions Enumerator.
 */
public enum PluginPermissions {
	
	/** Add server. */
	AddServer(0),
	/** Remove server. */
	RemoveServer(1),
	/** Send data. */
	SendData(2),
	/** Read data. */
	ReadData(3);
	
	/** Permission ID. */
	private int id;
	
	/**
	 * Instantiates a new plugin permission.
	 *
	 * @param id Permission ID
	 */
	private PluginPermissions(int id){
		this.id=id;
	}
	
	/**
	 * Get permission ID
	 *
	 * @return Permission ID
	 */
	public int getID(){
		return id;
	}
}
