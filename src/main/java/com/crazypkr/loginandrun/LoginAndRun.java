package com.crazypkr.loginandrun;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoginAndRun extends JavaPlugin {
	public static LoginAndRun instance;
	ConcurrentHashMap<UUID,ArrayList<StoredCommand>> usersCommands = new ConcurrentHashMap<UUID,ArrayList<StoredCommand>>();
	private Listener eventListener=new EventListener();
	
	public void onEnable() {
		instance=this;
		FileHandler.fileHandlerInstance.LoginAndRunLoadData();
		// Registers ability to use Permission
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public void onDisable() {
		instance=null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("loginandrun.manager") && cmd.getName().equalsIgnoreCase("loginandrun")){
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console can't run this command");
				return false;
			}
			
			if (args.length==0) {
				sender.sendMessage("Usage: /"+label+" [add|remove|list|enable|disable]");
				return false;
			}
			
			Player player = (Player) (sender); // Initialize player method as sender
			ArrayList<StoredCommand> playerCmds = this.usersCommands.get(player.getUniqueId());
			if (playerCmds == null) { // Checks if cmdlist is null
				playerCmds = new ArrayList<StoredCommand>(); // create a new cmdlist
				this.usersCommands.put(player.getUniqueId(),playerCmds); // store the empty array
			}
			
			if (args[0].equalsIgnoreCase("add")) {
				if (args.length<2) {
					sender.sendMessage("Usage: /"+label+" add [command]");
					return false;
				}
				
				String stringArgs = StringUtils.join(args, " ");
				StoredCommand storedCommand = new StoredCommand(stringArgs, true); // Create a new command with enabled "True"
				
				if (playerCmds.contains(stringArgs)) { // If command is already stored for user
					player.sendMessage(ChatColor.RED + "You already have that Enabled/Disabled!"); 
					return false;
				}
				
				player.sendMessage(ChatColor.GRAY + "Added [" + stringArgs + "] to be ran on login");
				playerCmds.add(storedCommand); // Add the command if cmdlist < 0
				
				FileHandler.fileHandlerInstance.LoginAndRunSaveData(); // Store to file
				
				return true;
			}
			
			if (args[0].equalsIgnoreCase("remove")) {
				if (args.length<2) {
					sender.sendMessage("Usage: /"+label+" remove (id)");
					return false;
				}
				try {
					int i = Integer.valueOf(args[1]);
					StoredCommand storedCommand = playerCmds.remove(i);
					player.sendMessage(ChatColor.GRAY + "Removed [" + storedCommand.strCommand + "]");
					return true;
				} catch (Exception e) {
					player.sendMessage(ChatColor.GRAY + "Invalid id");
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("list")) {
				if (playerCmds.isEmpty()){ // Checks if no commands are stored
					player.sendMessage(ChatColor.RED + "No commands set yet. Use /"+label+" add [command] to set it!");
					return false;
				}
				
				player.sendMessage(ChatColor.YELLOW + "**********************");
				for(int i=0; i<playerCmds.size(); i++) {
					StoredCommand storedCommand=playerCmds.get(i);
					if (storedCommand.enabled){
						player.sendMessage(ChatColor.YELLOW + "ID: "+i+" - " + storedCommand.strCommand);
					} else {
						player.sendMessage(ChatColor.GRAY + "ID: "+i+" - " + storedCommand.strCommand);
					}
				}
				player.sendMessage(ChatColor.YELLOW + "**********************");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("enable")) {
				if (args.length<2) {
					sender.sendMessage("Usage: /"+label+" enable (id|all)");
					return false;
				}
				
				if (args[1].equalsIgnoreCase("all")) {
					for(StoredCommand storedCommand : playerCmds) {
						storedCommand.enabled=true;
					}
					return true;
				}
				
				try {
					int i = Integer.valueOf(args[1]);
					StoredCommand storedCommand = playerCmds.get(i);
					storedCommand.enabled=true;
					player.sendMessage(ChatColor.GRAY + "Enabled [" + storedCommand.strCommand + "]");
					return true;
				} catch (Exception e) {
					player.sendMessage(ChatColor.GRAY + "Invalid id");
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("disable")) {
				if (args.length<2) {
					sender.sendMessage("Usage: /"+label+" disable (id|all)");
					return false;
				}
				if (args[1].equalsIgnoreCase("all")) {
					for(StoredCommand storedCommand : playerCmds) {
						storedCommand.enabled=false;
					}
					return true;
				}
				try {
					int i = Integer.valueOf(args[1]);
					StoredCommand storedCommand = playerCmds.get(i);
					storedCommand.enabled=false;
					player.sendMessage(ChatColor.GRAY + "Disabled [" + storedCommand.strCommand + "]");
					return true;
				} catch (Exception e) {
					player.sendMessage(ChatColor.GRAY + "Invalid id");
					return false;
				}
			}
		}
		return false;
	}
}

