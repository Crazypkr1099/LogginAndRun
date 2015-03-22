package com.crazypkr.testplugin;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {
	static ConcurrentHashMap<UUID, String> Usercommand = new ConcurrentHashMap<>();
	
	public static TestPlugin instance;
	private Listener eventListener=new EventListener();
	
	public void onEnable() {
		instance=this;
		this.getServer().getLogger().info("Useless plugin from useless developer. You're welcome.");
		

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) (sender);
		if (sender.hasPermission("testplugin.manager")){
			if (cmd.getName().equalsIgnoreCase("setonstart")){
				String ucommand = "";
				for(int i = 0; i < args.length;i++){
					ucommand += args[i];
				}
				Usercommand.put(player.getUniqueId(),ucommand);
				player.sendMessage("Added " + ucommand + "on startup!");
			}
		}
		return false;
	}
}

