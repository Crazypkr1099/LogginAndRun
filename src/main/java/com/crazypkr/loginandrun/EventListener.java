package com.crazypkr.loginandrun;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt){
		Player player = evt.getPlayer();
		if (player.hasPermission("testplugin.manager")){
			ArrayList<StoredCommand> cmdlist = LoginAndRun.instance.userCommands.get(player.getUniqueId());
			
			for (StoredCommand commands : cmdlist){
				if (commands.enabled == true){
					player.chat(commands.Command);
				}
			}
		}
	}
}
