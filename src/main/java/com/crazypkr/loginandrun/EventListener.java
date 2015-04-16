package com.crazypkr.loginandrun;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

class EventListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt){ // Method to check player joins the server
		Player player = evt.getPlayer(); // Get player
		if (player.hasPermission("loginandrun.manager")){ // Checks if player has permission
			ArrayList<StoredCommand> cmdlist = LoginAndRun.instance.usersCommands.get(player.getUniqueId());
			// ^ Stores user commands data in variable cmdlist
			
			for (StoredCommand commands : cmdlist){
				if (commands.enabled == true){ // If command is enabled
					player.chat(commands.strCommand); // Chat the command!
				}
			}
		}
	}
}
