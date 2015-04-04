package com.crazypkr.loginandrun;
import java.util.ArrayList;
import java.util.Iterator;
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
	ConcurrentHashMap<UUID,ArrayList<StoredCommand>> userCommands = new ConcurrentHashMap<UUID,ArrayList<StoredCommand>>();
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
		
		if (sender.hasPermission("loginandrun.manager")){ // If has perms...
			if (cmd.getName().equalsIgnoreCase("onstart")){ 
				if (cmdlist == null){
					cmdlist = new ArrayList<StoredCommand>();
					userCommands.put(player.getUniqueId(),cmdlist);
					}
					StoredCommand storedCommand = new StoredCommand(stringArgs, true);
					if (cmdlist.size() > 0 && !(args.length < 1)) {
						for (StoredCommand commands : cmdlist){
							if (!stringArgs.equalsIgnoreCase(commands.Command)){
								cmdlist.add(storedCommand);
								player.sendMessage(ChatColor.GRAY + "Added " + stringArgs + " to be ran on login");
								break;
							}
							player.sendMessage(ChatColor.RED + "You already have that Enabled/Disabled!");
						}
					}
					else{
						player.sendMessage(ChatColor.GRAY + "Added " + stringArgs + " to be ran on login");
						cmdlist.add(storedCommand);
					}
					FileHandler.fileHandlerInstance.LoginAndRunSaveData();
				}
			
		
			if (cmd.getName().equalsIgnoreCase("oscommands")){
				
				if (cmdlist == null || cmdlist.isEmpty()){
					player.sendMessage(ChatColor.RED + "No commands set yet. use /os <cmd> to set it!");
					return false;
				}
			
				player.sendMessage(ChatColor.YELLOW + "**********************");
				for (StoredCommand commands : cmdlist){
					player.sendMessage(ChatColor.YELLOW + "Command: (" + commands.Command + ") IsEnabled: " + commands.CheckEnabled());
				}
				player.sendMessage(ChatColor.YELLOW + "**********************");
			}
			
			if (cmd.getName().equalsIgnoreCase("osdisable")){
				for (StoredCommand commands : cmdlist){
					if (stringArgs.equalsIgnoreCase("all")){
						commands.enabled = false;
						ifAll = true;
					}
					else if (stringArgs.equalsIgnoreCase(commands.Command)){
						if (commands.enabled == false){
							player.sendMessage(ChatColor.RED + "You already have this disabled!");
						}
						else{
							commands.enabled = false;
							player.sendMessage(ChatColor.GRAY + "Disabled " + commands.Command);
						}
					}
				}
				
				if (ifAll == true){
					player.sendMessage(ChatColor.GRAY + "Disabled All Commands");
				}
				FileHandler.fileHandlerInstance.LoginAndRunSaveData();
			}
			
			if (cmd.getName().equalsIgnoreCase("osenable")){
				for (StoredCommand commands : cmdlist){
					if (stringArgs.equalsIgnoreCase("all")){
						commands.enabled = true;
						ifAll = true;
					}
					else if (stringArgs.equalsIgnoreCase(commands.Command)){
						if (commands.enabled == true){
							player.sendMessage(ChatColor.RED + "You already have this enabled!");
						}
						else{
							commands.enabled = true;
							player.sendMessage(ChatColor.GRAY + "Enabled " + commands.Command);	
						}
					}
				}
				if (ifAll == true){
					player.sendMessage(ChatColor.GRAY + "Enabled All Commands");
				}
				FileHandler.fileHandlerInstance.LoginAndRunSaveData();
			}
			
			if (cmd.getName().equalsIgnoreCase("osremove")){
					
				for (StoredCommand commands : cmdlist){
					Iterator<StoredCommand> itr = cmdlist.iterator();
					while(itr.hasNext()){
						if (stringArgs.equalsIgnoreCase("all")){
							ifAll = true;
							itr.remove();
						}
						else if (stringArgs.equalsIgnoreCase(commands.Command)){
							player.sendMessage(ChatColor.GRAY + "Removed " + commands.Command);
							itr.remove();
						}
					}
					Object next = itr.next();
					itr.remove();
				}
				
				if (ifAll == true){
					player.sendMessage(ChatColor.GRAY + "Removed All Commands");
				}
				FileHandler.fileHandlerInstance.LoginAndRunSaveData();
			}
		}
		return false;
	}
}
