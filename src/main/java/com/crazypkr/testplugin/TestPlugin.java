package com.crazypkr.testplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {
	public static TestPlugin instance;
	private Listener eventListener=new EventListener();
	
	public void onEnable() {
		instance=this;
		this.getServer().getLogger().info("Useless plugin from useless developer. You're welcome.");
		

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		if (sender.hasPermission("testplugin.manager")){
			if (cmd.getName().equalsIgnoreCase("setonstart")){
				
				if (args[0].equalsIgnoreCase("fly")){
					if (args[1].equalsIgnoreCase("true")){
						player.setAllowFlight(true);
						player.sendMessage("Fly Enabled On Startup is TRUE");
					}
					else if (args[1].equalsIgnoreCase("false")){
						player.setAllowFlight(false);
						player.sendMessage("Fly Enabled On Startup is FALSE");
					}
				}
				
				else if (args[0].equalsIgnoreCase("op")){
					if (args[1].equalsIgnoreCase("true")){
						player.setOp(true);
						player.sendMessage("OP Enabled On Startup is TRUE");
					}
					else if (args[1].equalsIgnoreCase("false")){
						player.setOp(false);
						player.sendMessage("Fly Enabled On Startup is FALSE");
					}
				}
			}
		}
		return false;
	}
}

