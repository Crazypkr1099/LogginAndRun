package com.crazypkr.logginandrun;
import java.util.ArrayList;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogginAndRun extends JavaPlugin {
	ConcurrentHashMap<UUID,ArrayList<StoredCommand>> userCommands = new ConcurrentHashMap<UUID,ArrayList<StoredCommand>>();
	public static LogginAndRun instance;
	private Listener eventListener=new EventListener();
	public void onEnable() {
		instance=this;
		FileHandler.fileHandlerInstance.LogginAndRunLoadData();
		// Registers ability to use Permission
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) (sender); // Initialize player method as sender
		ArrayList<StoredCommand> cmdlist = userCommands.get(player.getUniqueId());
		if (sender.hasPermission("logginandrun.manager")){ // If has perms...
			if (cmd.getName().equalsIgnoreCase("osenable")){ 
				if (cmdlist == null){
					cmdlist = new ArrayList<StoredCommand>();
					userCommands.put(player.getUniqueId(),cmdlist);
				}
				
				StoredCommand storedCommand = new StoredCommand(args.toString(),true);
				cmdlist.add(storedCommand);
				
			
				
				if (cmdlist == null || cmdlist.isEmpty()){
					player.sendMessage(ChatColor.RED + "No commands set yet. use /os <cmd> to set it!");
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("oscommands")){
			
			if (cmdlist == null || cmdlist.isEmpty()){
				player.sendMessage(ChatColor.RED + "No commands set yet. use /os <cmd> to set it!");
			}
			
			for (StoredCommand storedCommand : cmdlist){
				player.sendMessage(storedCommand.Command + " - Status:" + (storedCommand.enabled ? "enabled" : "diabled"));
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("osdisable")){
			for (StoredCommand storedCommand : cmdlist){
				if (storedCommand.Command.equalsIgnoreCase(args.toString())){
					storedCommand.enabled = false;
				}	
			}
		}
		return false;
	}
}
