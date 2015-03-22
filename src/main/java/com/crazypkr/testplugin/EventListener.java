package com.crazypkr.testplugin;

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
		TestPlugin.instance.getServer().getLogger().info("onPlayerJoin triggered.");
		Player player = evt.getPlayer();
		player.sendMessage(player.getName() + " joined the server with this useless plugin. Yes, it is.");
		if (player.hasPermission("testplugin.manager")){
			for(Entry<UUID, ArrayList<String>> entry : TestPlugin.instance.userCommands.entrySet()){
	            for (int i = 0; i < entry.getValue().size();i++){
	            	if (entry.getKey() == player.getUniqueId()){
	            		player.chat("/" + entry.getValue());
	            		player.sendMessage("You have just ran the command:" + entry.getValue());
					}
	            }
	        }
		}else if (player.hasPermission("testplugin.owner")){
			player.sendMessage("You are the owner!");
		}else
			player.sendMessage("You are a regular player");
	}
}
