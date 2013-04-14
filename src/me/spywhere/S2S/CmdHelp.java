package me.spywhere.S2S;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHelp {
	private CommandSender sender;
	private Command command;
	private String commandLabel;
	private String[] args;
	private int currentArg = 0;
	private boolean valid = false;
	
	public CmdHelp(CommandSender sender, Command command, String commandLabel, String[] args) {
		this.sender = sender;
		this.command = command;
		this.commandLabel = commandLabel;
		this.args = args;
	}
	
	public CmdHelp splitCmd() {
		return new CmdHelp(sender, command, commandLabel, Arrays.copyOfRange(args, currentArg, args.length));
	}
	
	public boolean isPlayer() {
		return (sender instanceof Player);
	}
	
	public boolean isConsole() {
		return !isPlayer();
	}
	
	public boolean is(String cmd) {
		valid = command.getName().equalsIgnoreCase(cmd);
		return valid;
	}
	
	public boolean aliasIs(String cmd) {
		valid = false;
		if(commandLabel.equalsIgnoreCase(cmd)){
			valid = true;
			return valid;
		}
		if(command.getName().equalsIgnoreCase(cmd)){
			valid = true;
			return valid;
		}
		for(String alias:command.getAliases()){
			if(alias.equalsIgnoreCase(cmd)){
				valid = true;
				return valid;
			}
		}
		return valid;
	}
	
	public boolean argIs(String arg) {
		boolean is = false;
		if(hasArgs(false)){
			is = getArg().equalsIgnoreCase(arg);
		}
		if(is){
			currentArg++;
		}
		valid = is;
		return is;
	}
	
	@Override
	public String toString() {
		String str = commandLabel;
		for(String s:args){
			if(!str.isEmpty()){
				str += " ";
			}
			str += s;
		}
		return str;
	}
	
	public String getString() {
		String str = "";
		for(int i = currentArg;i < args.length;i++){
			if(!str.isEmpty()){
				str += " ";
			}
			str += args[i];
		}
		return str;
	}
	
	public String getArg() {
		if(hasArgs(false)){ return args[currentArg]; }
		return null;
	}
	
	public String nextArg() {
		if(hasArgs(false)){ return args[currentArg++]; }
		return null;
	}
	
	public boolean argMatch(String regex) {
		String str = getArg();
		if(str == null){ return false; }
		return str.matches(regex);
	}
	
	public boolean nextArgMatch(String regex) {
		String str = nextArg();
		if(str == null){ return false; }
		return str.matches(regex);
	}
	
	public boolean hasArgs(boolean set) {
		if(set){
			valid = args.length > currentArg;
			return valid;
		}else{
			return args.length > currentArg;
		}
	}
	
	public boolean hasArgs() {
		return hasArgs(true);
	}
	
	public int currentArg() {
		return currentArg;
	}
	
	public boolean validate(boolean validate) {
		valid = validate;
		return valid;
	}
	
	public boolean validate() {
		return valid;
	}
}
