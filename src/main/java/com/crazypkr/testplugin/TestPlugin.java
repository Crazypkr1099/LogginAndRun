package com.crazypkr.testplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("test")){
			sender.sendMessage("Testing my command out!");
			return true;
		}
		return false;
	}
	
	public void onPlayerJoin(PlayerJoinEvent evt){
		Player player = evt.getPlayer();
		if (player.hasPermission("testplugin.manager")){
			player.sendMessage("You are a manager!");
		}else if (player.hasPermission("testplugin.owner")){
			player.sendMessage("You are the owner!");
		}else
			player.sendMessage("You are a regular player");
	}
	
	
	
}

	