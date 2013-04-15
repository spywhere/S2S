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

//To do:
//Return all "possible" path from specified path
//


/**
 * Enhanced YML File Configuration.
 * Easy to use, fully customizable.<br/>
 * Use native {@link YamlConfiguration} and {@link FileConfiguration}.
 * 
 * @version 0.3
 * 
 * @author spywhere
 * 
 * @see FileConfiguration
 * @see YamlConfiguration
 */
public class YMLIO {
	
	/** The yaml file configuration. */
	private FileConfiguration yml=new YamlConfiguration();
	
	/** The yaml file. */
	private File file=null;
	
	/** The force save mode. */
	private boolean force=false;
	
	/**
	 * Instantiates a new Enhanced YML File Configuration.
	 *
	 * @param file the yaml file to read
	 * @throws FileNotFoundException the exception that will be thrown if file not found
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InvalidConfigurationException the exception that will be thrown if the file is not a valid yaml configuration file
	 */
	public YMLIO(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		this.file=file;
		if(file.exists()){
			this.yml.load(this.file);
		}
	}
	
	/**
	 * Contains specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @return true, if contains
	 */
	public boolean contains(final String path) {
		return this.hasPath(path, "");
	}
	
	/**
	 * Gets boolean from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets double from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets integer from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets {@link ItemStack} from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets long from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets object from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets {@link OfflinePlayer} from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets string from specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
	 * @return the value
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
	 * Gets the paths and values as map.
	 *
	 * @since 0.2
	 *
	 * @param path the path
	 * @return the map of path and value
	 */
	public Map<String, Object> getAsMap(final String path) {
		final Map<String, Object> map=new HashMap<String, Object>();
		if(this.yml.contains(path)&&this.yml.isConfigurationSection(path)){
			final Set<String> keys=this.yml.getConfigurationSection(path).getKeys(false);
			if(keys.size()>0){
				final Object[] key=keys.toArray();
				for(final Object element:key){
					map.put(path+"."+(String) element, this.getObject(path+"."+(String) element));
				}
			}
		}
		return map;
	}
	
	/**
	 * Gets the full paths list.
	 *
	 * @since 0.3
	 *
	 * @param path the path
	 * @return the list of full paths
	 */
	public List<String> getAsFullPathList(final String path) {
		final List<String> list=new ArrayList<String>();
		if(this.yml.contains(path)&&this.yml.isConfigurationSection(path)){
			final Set<String> keys=this.yml.getConfigurationSection(path).getKeys(false);
			if(keys.size()>0){
				final Object[] key=keys.toArray();
				for(final Object element:key){
					list.add(path+"."+(String) element);
				}
			}
		}
		return list;
	}
	
	/**
	 * Gets the path names list.
	 *
	 * @since 0.2
	 *
	 * @param path the path
	 * @return the list of path names
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
	 * Gets the values list.
	 *
	 * @since 0.2
	 *
	 * @param path the path
	 * @return the list of path values
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the boolean value
	 */
	public boolean getBoolean(final String path) {
		return this.yml.getBoolean(path);
	}
	
	/**
	 * Gets the boolean list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the double value
	 */
	public double getDouble(final String path) {
		return this.yml.getDouble(path);
	}
	
	/**
	 * Gets the double list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * Gets the read yaml file.
	 *
	 * @since 0.1
	 *
	 * @return the read yaml file
	 */
	public File getFile() {
		return this.file;
	}
	
	/**
	 * Gets the configuration file.
	 *
	 * @since 0.1
	 *
	 * @return the configuration file
	 */
	public FileConfiguration getFileConfiguration() {
		return this.yml;
	}
	
	/**
	 * Gets the float list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * Gets the file header.
	 *
	 * @since 0.1
	 *
	 * @return the file header
	 */
	public String getHeader() {
		return this.yml.options().header();
	}
	
	/**
	 * Gets the file header.
	 *
	 * @since 0.1
	 *
	 * @param def the default header
	 * @return the file header
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
	 * Gets the integer.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the integer value
	 */
	public int getInt(final String path) {
		return this.yml.getInt(path);
	}
	
	/**
	 * Gets the integer list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
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
	 * Gets the {@link ItemStack}.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the {@link ItemStack} value
	 */
	public ItemStack getItemStack(final String path) {
		return this.yml.getItemStack(path);
	}
	
	/**
	 * Gets the list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the long value
	 */
	public long getLong(final String path) {
		return this.yml.getLong(path);
	}
	
	/**
	 * Gets the long list.
	 *
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
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
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default value
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the object
	 */
	public Object getObject(final String path) {
		return this.yml.get(path);
	}
	
	/**
	 * Gets the {@link OfflinePlayer}.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @return the {@link OfflinePlayer} value
	 */
	public OfflinePlayer getOfflinePlayer(final String path) {
		return this.yml.getOfflinePlayer(path);
	}
	
	/**
	 * Gets the path (without case-sensitive).
	 *
	 * @since 0.1
	 *
	 * @param path the path (without case-sensitive)
	 * @return the path (with case-sensitive)
	 */
	public String getPath(final String path) {
		return this.getPath(path, path, "");
	}
	
	/**
	 * Gets the path.
	 * 
	 * @param sPath the source path
	 * @param sPath2 the source path 2
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
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * @since 0.1
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
	 * @since 0.1
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
	 * @since 0.1
	 *
	 * @param path the path
	 * @param def the default list
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
	 * @param sPath the source path
	 * @param path the path
	 * @return true, if contains
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
	 * Save the configurations.
	 * 
	 * @since 0.1
	 */
	public void save() {
		try{
			this.yml.save(this.file);
		}catch (final IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the object to specified path.
	 *
	 * @since 0.1
	 *
	 * @param path the path
	 * @param obj the object
	 */
	public void set(final String path, final Object obj) {
		this.yml.set(path, obj);
	}
	
	/**
	 * Sets the configuration file.
	 *
	 * @since 0.1
	 *
	 * @param file the file to read
	 * @param fileConfig the configuration file
	 */
	public void setFileConfiguration(final File file, final FileConfiguration fileConfig) {
		this.file=file;
		this.yml=fileConfig;
	}
	
	/**
	 * Sets the force save mode.
	 *
	 * @since 0.1
	 *
	 * @param force the new force save mode
	 */
	public void setForceSave(final boolean force) {
		this.force=force;
	}
	
	/**
	 * Sets the file header.
	 *
	 * @since 0.1
	 *
	 * @param str the new file header
	 */
	public void setHeader(final String str) {
		this.yml.options().header(str);
	}
}
