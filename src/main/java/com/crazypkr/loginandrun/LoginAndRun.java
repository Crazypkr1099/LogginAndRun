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
	ConcurrentHashMap<UUID,ArrayList<StoredCommand>> userCommands = 
			new ConcurrentHashMap<UUID,ArrayList<StoredCommand>>();
	public static LoginAndRun instance;
	private Listener eventListener=new EventListener();
	public void onEnable() {
		instance=this;
		FileHandler.fileHandlerInstance.LoginAndRunLoadData();
		// Registers ability to use Permission
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		String stringArgs = StringUtils.join(args, " ");
		Player player = (Player) (sender); // Initialize player method as sender
		ArrayList<StoredCommand> cmdlist = userCommands.get(player.getUniqueId());
		boolean ifAll = false;
		
		if (sender.hasPermission("loginandrun.manager")){ // If player has permisison
			if (cmd.getName().equalsIgnoreCase("onstart")){  // If command is "onstart"
				if (cmdlist == null){ // Checks if cmdlist is null
					cmdlist = new ArrayList<StoredCommand>(); // create a new cmdlist
					userCommands.put(player.getUniqueId(),cmdlist); // store the empty array
					}
					StoredCommand storedCommand = new StoredCommand(stringArgs, true); // Create a new command with enabled "True"
					if (cmdlist.size() > 0) { // Checks cmdlist size
						for (StoredCommand commands : cmdlist){
							if (!stringArgs.equalsIgnoreCase(commands.Command)){ // If command isn't already stored for user
								cmdlist.add(storedCommand); // Store the command
								player.sendMessage(ChatColor.GRAY + "Added [" + stringArgs + "] to be ran on login");
								break;
							}
							// If command is already stored for user
							player.sendMessage(ChatColor.RED + "You already have that Enabled/Disabled!"); 
							return false;
						}
					}
					else{
						player.sendMessage(ChatColor.GRAY + "Added [" + stringArgs + "] to be ran on login");
						cmdlist.add(storedCommand); // Add the command if cmdlist < 0
					}
					FileHandler.fileHandlerInstance.LoginAndRunSaveData(); // Store to file
				}
			
		
			if (cmd.getName().equalsIgnoreCase("oscommands")){ // If command is "oscommands"
				
				if (cmdlist == null || cmdlist.isEmpty()){ // Checks if no commands are stored
					player.sendMessage(ChatColor.RED + "No commands set yet. use /os <cmd> to set it!");
					return false;
				}
				// Else, list all commands that the user has
				player.sendMessage(ChatColor.YELLOW + "**********************");
				for (StoredCommand commands : cmdlist){
					player.sendMessage(ChatColor.YELLOW + "Command: (" + commands.Command + ") IsEnabled: " + commands.enabled);
				}
				player.sendMessage(ChatColor.YELLOW + "**********************");
			}
			
			if (cmd.getName().equalsIgnoreCase("osdisable")){ // If command is "osdisable"
				for (StoredCommand commands : cmdlist){  
					if (stringArgs.equalsIgnoreCase("all")){ // Checks if stringArgs is "all"
						commands.enabled = false; // Loops to disable all commands
						ifAll = true; 
					}
					else if (stringArgs.equalsIgnoreCase(commands.Command)){ // If stringArgs is equal to a command
						if (commands.enabled == false){ // If enabled already equals false
							player.sendMessage(ChatColor.RED + "You already have this disabled!"); 
							return false;
						}
						else{
							commands.enabled = false; // disable command
							player.sendMessage(ChatColor.GRAY + "Disabled [" + commands.Command + "]");
						}
					}
				}
				
				if (ifAll == true){
					player.sendMessage(ChatColor.GRAY + "Disabled All Commands");
				}
				FileHandler.fileHandlerInstance.LoginAndRunSaveData(); // Store data
			}
			
			if (cmd.getName().equalsIgnoreCase("osenable")){ // If command is "osenable"
				for (StoredCommand commands : cmdlist){ 
					if (stringArgs.equalsIgnoreCase("all")){ // Check if stringArgs is equal to "all"
						commands.enabled = true; // Enable all commands
						ifAll = true;
					}
					else if (stringArgs.equalsIgnoreCase(commands.Command)){
						if (commands.enabled == true){ // If already enabled
							player.sendMessage(ChatColor.RED + "You already have this enabled!");
							return false;
						}
						else{
							commands.enabled = true; // Enable command
							player.sendMessage(ChatColor.GRAY + "Enabled [" + commands.Command + "]");	
						}
					}
				}
				if (ifAll == true){
					player.sendMessage(ChatColor.GRAY + "Enabled All Commands");
				}
				FileHandler.fileHandlerInstance.LoginAndRunSaveData(); // Store data
			}
			
			if (cmd.getName().equalsIgnoreCase("osremove")){ // If command is "osremove"
			
				if (stringArgs.equalsIgnoreCase("all")){ // Check if stringArgs is equal to "all"
					cmdlist.clear(); //clear players command data
					player.sendMessage(ChatColor.GRAY + "Removed All Commands");
				}
				
				else{
				
					for(int i = 0; i < cmdlist.size(); i++){ // Loops players command data
						if (stringArgs.equalsIgnoreCase(cmdlist.get(i).Command)){ // If command is equal to stringArgs
							player.sendMessage(ChatColor.GRAY + "Removed [" + cmdlist.get(i).Command);
							cmdlist.remove(i); // Remove command
						}
					}
				}
			}
		}
		return false;
	}
}

