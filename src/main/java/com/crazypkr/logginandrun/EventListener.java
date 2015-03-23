package com.crazypkr.logginandrun;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt){
		Player player = evt.getPlayer();
		player.sendMessage(player.getName() + " joined the server with this useless plugin. Yes, it is.");
		if (player.hasPermission("testplugin.manager")){
			for(Entry<UUID, ArrayList<String>> entry : LogginAndRun.instance.userCommands.entrySet()){
				player.sendMessage("Commands being runned ");
				for (String val : entry.getValue()){
					player.chat(val);
					player.sendMessage("Command:" + val + " was initiated");
	            }
	        }
		}else if (player.hasPermission("testplugin.owner")){
			player.sendMessage("You are the owner!");
		}else
			player.sendMessage("You are a regular player");
	}
}
