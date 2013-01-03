package me.spywhere.S2S;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * YMLIO Class.
 */
public class YMLIO {
	
	/** YML Configuration. */
	private FileConfiguration yml=new YamlConfiguration();
	
	/** File. */
	private File file=null;
	
	/** Force save. */
	private boolean force=false;
	
	/**
	 * Instantiates a new YMLIO.
	 *
	 * @param file File
	 * @throws FileNotFoundException FileNotFoundException
	 * @throws IOException I/O Exception
	 * @throws InvalidConfigurationException InvalidConfigurationException
	 */
	public YMLIO(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		this.file=file;
		if(file.exists()){
			this.yml.load(this.file);
		}
	}
	
	/**
	 * Contains path.
	 *
	 * @param path Path
	 * @return true, if exists
	 */
	public boolean contains(final String path) {
		return this.hasPath(path, "");
	}
	
	/**
	 * Get boolean.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public boolean get(final String path, final boolean def) {
		boolean obj=this.yml.getBoolean(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get double.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public double get(final String path, final double def) {
		double obj=this.yml.getDouble(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get integer.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public int get(final String path, final int def) {
		int obj=this.yml.getInt(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get item stack.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public ItemStack get(final String path, final ItemStack def) {
		ItemStack obj=this.yml.getItemStack(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get long.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public long get(final String path, final long def) {
		long obj=this.yml.getLong(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get object.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public Object get(final String path, final Object def) {
		Object obj=this.yml.get(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get offline player.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public OfflinePlayer get(final String path, final OfflinePlayer def) {
		OfflinePlayer obj=this.yml.getOfflinePlayer(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Get string.
	 *
	 * @param path Path
	 * @param def Default value
	 * @return Value
	 */
	public String get(final String path, final String def) {
		String obj=this.yml.getString(path, def);
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the as map.
	 *
	 * @param path the path
	 * @return the as map
	 */
	public Map<String, Object> getAsMap(final String path) {
		final Map<String, Object> map=new HashMap<String, Object>();
		if(this.yml.contains(path)&&this.yml.isConfigurationSection(path)){
			final Set<String> keys=this.yml.getConfigurationSection(path).getKeys(false);
			if(keys.size()>0){
				final Object[] key=keys.toArray();
				for(final Object element:key){
					map.put((String) element, this.getObject(path+"."+(String) element));
				}
			}
		}
		return map;
	}
	
	/**
	 * Gets the as path list.
	 *
	 * @param path the path
	 * @return the as path list
	 */
	public List<String> getAsPathList(final String path) {
		final List<String> list=new ArrayList<String>();
		if(this.yml.contains(path)&&this.yml.isConfigurationSection(path)){
			final Set<String> keys=this.yml.getConfigurationSection(path).getKeys(false);
			if(keys.size()>0){
				final Object[] key=keys.toArray();
				for(final Object element:key){
					list.add((String) element);
				}
			}
		}
		return list;
	}
	
	/**
	 * Gets the as value list.
	 *
	 * @param path the path
	 * @return the as value list
	 */
	public List<Object> getAsValueList(final String path) {
		final List<Object> list=new ArrayList<Object>();
		if(this.yml.contains(path)&&this.yml.isConfigurationSection(path)){
			final Set<String> keys=this.yml.getConfigurationSection(path).getKeys(false);
			if(keys.size()>0){
				final Object[] key=keys.toArray();
				for(final Object element:key){
					list.add(this.getObject(path+"."+(String) element));
				}
			}
		}
		return list;
	}
	
	/**
	 * Gets the boolean.
	 *
	 * @param path the path
	 * @return the boolean
	 */
	public boolean getBoolean(final String path) {
		return this.yml.getBoolean(path);
	}
	
	/**
	 * Gets the boolean list.
	 *
	 * @param path the path
	 * @return the boolean list
	 */
	public List<Boolean> getBooleanList(final String path) {
		return this.yml.getBooleanList(path);
	}
	
	/**
	 * Gets the boolean list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the boolean list
	 */
	public List<Boolean> getBooleanList(final String path, final List<Boolean> def) {
		List<Boolean> obj=def;
		if(this.yml.contains(path)){
			obj=this.getBooleanList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the byte list.
	 *
	 * @param path the path
	 * @return the byte list
	 */
	public List<Byte> getByteList(final String path) {
		return this.yml.getByteList(path);
	}
	
	/**
	 * Gets the byte list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the byte list
	 */
	public List<Byte> getByteList(final String path, final List<Byte> def) {
		List<Byte> obj=def;
		if(this.yml.contains(path)){
			obj=this.getByteList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the character list.
	 *
	 * @param path the path
	 * @return the character list
	 */
	public List<Character> getCharacterList(final String path) {
		return this.yml.getCharacterList(path);
	}
	
	/**
	 * Gets the character list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the character list
	 */
	public List<Character> getCharacterList(final String path, final List<Character> def) {
		List<Character> obj=def;
		if(this.yml.contains(path)){
			obj=this.getCharacterList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the double.
	 *
	 * @param path the path
	 * @return the double
	 */
	public double getDouble(final String path) {
		return this.yml.getDouble(path);
	}
	
	/**
	 * Gets the double list.
	 *
	 * @param path the path
	 * @return the double list
	 */
	public List<Double> getDoubleList(final String path) {
		return this.yml.getDoubleList(path);
	}
	
	/**
	 * Gets the double list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the double list
	 */
	public List<Double> getDoubleList(final String path, final List<Double> def) {
		List<Double> obj=def;
		if(this.yml.contains(path)){
			obj=this.getDoubleList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}
	
	/**
	 * Gets the file configuration.
	 *
	 * @return the file configuration
	 */
	public FileConfiguration getFileConfiguration() {
		return this.yml;
	}
	
	/**
	 * Gets the float list.
	 *
	 * @param path the path
	 * @return the float list
	 */
	public List<Float> getFloatList(final String path) {
		return this.yml.getFloatList(path);
	}
	
	/**
	 * Gets the float list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the float list
	 */
	public List<Float> getFloatList(final String path, final List<Float> def) {
		List<Float> obj=def;
		if(this.yml.contains(path)){
			obj=this.getFloatList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public String getHeader() {
		return this.yml.options().header();
	}
	
	/**
	 * Gets the header.
	 *
	 * @param def the def
	 * @return the header
	 */
	public String getHeader(final String def) {
		String str=this.getHeader();
		if(str.isEmpty()){
			str=def;
		}
		if(this.force){
			str=def;
		}
		this.setHeader(str);
		return str;
	}
	
	/**
	 * Gets the int.
	 *
	 * @param path the path
	 * @return the int
	 */
	public int getInt(final String path) {
		return this.yml.getInt(path);
	}
	
	/**
	 * Gets the integer list.
	 *
	 * @param path the path
	 * @return the integer list
	 */
	public List<Integer> getIntegerList(final String path) {
		return this.yml.getIntegerList(path);
	}
	
	/**
	 * Gets the integer list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the integer list
	 */
	public List<Integer> getIntegerList(final String path, final List<Integer> def) {
		List<Integer> obj=def;
		if(this.yml.contains(path)){
			obj=this.getIntegerList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the item stack.
	 *
	 * @param path the path
	 * @return the item stack
	 */
	public ItemStack getItemStack(final String path) {
		return this.yml.getItemStack(path);
	}
	
	/**
	 * Gets the list.
	 *
	 * @param path the path
	 * @return the list
	 */
	public List<?> getList(final String path) {
		return this.yml.getList(path);
	}
	
	/**
	 * Gets the list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the list
	 */
	public List<?> getList(final String path, final List<?> def) {
		List<?> obj=def;
		if(this.yml.contains(path)){
			obj=this.getList(path, def);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the long.
	 *
	 * @param path the path
	 * @return the long
	 */
	public long getLong(final String path) {
		return this.yml.getLong(path);
	}
	
	/**
	 * Gets the long list.
	 *
	 * @param path the path
	 * @return the long list
	 */
	public List<Long> getLongList(final String path) {
		return this.yml.getLongList(path);
	}
	
	/**
	 * Gets the long list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the long list
	 */
	public List<Long> getLongList(final String path, final List<Long> def) {
		List<Long> obj=def;
		if(this.yml.contains(path)){
			obj=this.getLongList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the map list.
	 *
	 * @param path the path
	 * @return the map list
	 */
	public List<Map<?, ?>> getMapList(final String path) {
		return this.yml.getMapList(path);
	}
	
	/**
	 * Gets the map list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the map list
	 */
	public List<Map<?, ?>> getMapList(final String path, final List<Map<?, ?>> def) {
		List<Map<?, ?>> obj=def;
		if(this.yml.contains(path)){
			obj=this.getMapList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the object.
	 *
	 * @param path the path
	 * @return the object
	 */
	public Object getObject(final String path) {
		return this.yml.get(path);
	}
	
	/**
	 * Gets the offline player.
	 *
	 * @param path the path
	 * @return the offline player
	 */
	public OfflinePlayer getOfflinePlayer(final String path) {
		return this.yml.getOfflinePlayer(path);
	}
	
	/**
	 * Gets the path.
	 *
	 * @param path the path
	 * @return the path
	 */
	public String getPath(final String path) {
		return this.getPath(path, path, "");
	}
	
	/**
	 * Gets the path.
	 *
	 * @param sPath the s path
	 * @param sPath2 the s path2
	 * @param path the path
	 * @return the path
	 */
	private String getPath(final String sPath, String sPath2, final String path) {
		String pathPrefix="";
		if(!path.isEmpty()){
			pathPrefix=path+".";
		}
		if(this.yml.isConfigurationSection(path)){
			final Set<String> key=this.yml.getConfigurationSection(path).getKeys(false);
			if(key.size()>0){
				final Object[] paths=key.toArray();
				for(final Object path2:paths){
					final String tmp=this.getPath(sPath, sPath2, pathPrefix+path2.toString());
					if(tmp.equalsIgnoreCase(sPath)){
						return tmp;
					}else{
						sPath2=tmp;
					}
				}
			}
		}else{
			return path;
		}
		if(path.isEmpty()){
			return sPath;
		}else{
			return sPath2;
		}
	}
	
	/**
	 * Gets the short list.
	 *
	 * @param path the path
	 * @return the short list
	 */
	public List<Short> getShortList(final String path) {
		return this.yml.getShortList(path);
	}
	
	/**
	 * Gets the short list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the short list
	 */
	public List<Short> getShortList(final String path, final List<Short> def) {
		List<Short> obj=def;
		if(this.yml.contains(path)){
			obj=this.getShortList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Gets the string.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String getString(final String path) {
		return this.yml.getString(path);
	}
	
	/**
	 * Gets the string list.
	 *
	 * @param path the path
	 * @return the string list
	 */
	public List<String> getStringList(final String path) {
		return this.yml.getStringList(path);
	}
	
	/**
	 * Gets the string list.
	 *
	 * @param path the path
	 * @param def the def
	 * @return the string list
	 */
	public List<String> getStringList(final String path, final List<String> def) {
		List<String> obj=def;
		if(this.yml.contains(path)){
			obj=this.getStringList(path);
		}
		if(this.force){
			obj=def;
		}
		this.set(path, obj);
		return obj;
	}
	
	/**
	 * Checks for path.
	 *
	 * @param sPath the s path
	 * @param path the path
	 * @return true, if successful
	 */
	private boolean hasPath(final String sPath, final String path) {
		String pathPrefix="";
		if(!path.isEmpty()){
			pathPrefix=path+".";
		}
		if(this.yml.isConfigurationSection(path)){
			final Set<String> key=this.yml.getConfigurationSection(path).getKeys(false);
			if(key.size()>0){
				final Object[] paths=key.toArray();
				for(final Object path2:paths){
					if(this.hasPath(sPath, pathPrefix+path2.toString())){ return true; }
				}
			}
		}else{
			return sPath.equalsIgnoreCase(path);
		}
		return false;
	}
	
	/**
	 * Save.
	 */
	public void save() {
		try{
			this.yml.save(this.file);
		}catch (final IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the.
	 *
	 * @param path the path
	 * @param obj the obj
	 */
	public void set(final String path, final Object obj) {
		this.yml.set(path, obj);
	}
	
	/**
	 * Sets the file configuration.
	 *
	 * @param file the file
	 * @param fileConfig the file config
	 */
	public void setFileConfiguration(final File file, final FileConfiguration fileConfig) {
		this.file=file;
		this.yml=fileConfig;
	}
	
	/**
	 * Sets the force save.
	 *
	 * @param force the new force save
	 */
	public void setForceSave(final boolean force) {
		this.force=force;
	}
	
	/**
	 * Sets the header.
	 *
	 * @param str the new header
	 */
	public void setHeader(final String str) {
		this.yml.options().header(str);
	}
}
