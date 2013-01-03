package me.spywhere.S2S;

public enum PluginPermissions {
	AddServer(0),RemoveServer(1),SendData(2),ReadData(3);
	
	private int id;
	private PluginPermissions(int id){
		this.id=id;
	}
	
	public int getID(){
		return id;
	}
}
