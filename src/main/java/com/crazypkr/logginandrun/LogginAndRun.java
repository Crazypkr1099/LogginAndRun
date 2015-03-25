package com.crazypkr.logginandrun;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogginAndRun extends JavaPlugin {
	ConcurrentHashMap<UUID,ArrayList<String>> userCommands = new ConcurrentHashMap<>();
	
	public static LogginAndRun instance;
	private Listener eventListener=new EventListener();
	
	public void onEnable() {
		instance=this;
		this.getServer().getLogger().info("Useless plugin from useless developer. You're welcome.");
		

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) (sender);
		if (sender.hasPermission("logginandrun.manager")){
			if (cmd.getName().equalsIgnoreCase("setonstart")){
				String ustrcommand = "";
				for (String s:args){
					ustrcommand += s + " ";
				}
				ArrayList<String> usersCArray = userCommands.get(player.getUniqueId());
				if (usersCArray == null){
					usersCArray = new ArrayList<String>();
					userCommands.put(player.getUniqueId(),usersCArray);
				}
				
				usersCArray.add(ustrcommand);
			}
			
			if (cmd.getName().equalsIgnoreCase("onstartcommands")){
				try{
					ArrayList<String> commandlist = userCommands.get(player.getUniqueId());
					String listofcmd = "";
					for (String s:commandlist){
						listofcmd += s + "\n";
					}
					player.sendMessage(ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n" +
										"Loggin and Run Commands\n"	+
										"Scheduled to be on\n"		+
										"Login\n"					+
										"+-+-+-+-+-+-+-+-+-+-+-+\n"	+ ChatColor.RED + ChatColor.BOLD +
										listofcmd + "\n");
					
				}
				
				catch (Exception e){}
			}
			
			if (cmd.getName().equalsIgnoreCase("removeonstart")){
				
				String ustrcommand = " ";
				for (String s:args){
					ustrcommand += s + " ";
				}
				ArrayList<String> usersCArray = userCommands.get(player.getUniqueId());
				int count = 0;
				Iterator<String> ite = usersCArray.iterator();
				while(ite.hasNext()){
					String value = ite.next();
					if (ustrcommand.contains(value)){
						player.sendMessage("Contains!");
						ite.remove();
						player.sendMessage(ChatColor.YELLOW + "Removed " + value + " from running on login");
					}

				}
			}
				
		}
		
		
		return false;
	}
}

