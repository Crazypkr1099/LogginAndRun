package com.crazypkr.loginandrun;

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
				System.out.println("hi");
				
			}catch (Exception e){}
			
		}else
			player.sendMessage("You are a regular player");
	}
}
