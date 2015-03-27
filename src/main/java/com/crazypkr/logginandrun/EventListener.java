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
		if (player.hasPermission("testplugin.manager")){
			try{
				ArrayList<String> loggedinuser = LogginAndRun.instance.userCommands.get(player.getUniqueId());
				for (String s: loggedinuser){
					String uStrCommand = "";
					String[] splitString = s.split(" ");
					for (int i = 0; i < splitString.length-1; i++){
						uStrCommand += splitString[i] + " ";
					}
					if (splitString[splitString.length-1].equalsIgnoreCase("on")){
						player.chat(uStrCommand);
					}
				}
			}catch (Exception e){}
			
		}else
			player.sendMessage("You are a regular player");
	}
}
