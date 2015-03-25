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
				player.sendMessage(ChatColor.YELLOW + "Added " + ustrcommand + "to run on login");
			}
			if (cmd.getName().equalsIgnoreCase("onstartcommands")){
					ArrayList<String> commandlist = userCommands.get(player.getUniqueId());
					if (commandlist == null || commandlist.size() == 0){
						player.sendMessage(ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n" +
								ChatColor.RED + "Theres no commands being\n"	+
								"runned on log in\n"		+
								ChatColor.GREEN + "Use /setonstart <cmd>\n" +
								"to add a command\n" + 
								ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n");
						
						}
					
				
					else{
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
				}
			if (cmd.getName().equalsIgnoreCase("removeonstart")){
				boolean Find = false;
				String ustrcommand = " ";
				for (String s:args){
					ustrcommand += s + " ";
				}
				ArrayList<String> usersCArray = userCommands.get(player.getUniqueId());
				Iterator<String> ite = usersCArray.iterator();
				while(ite.hasNext()){
					String value = ite.next();
					if (ustrcommand.contains(value)){
						ite.remove();
						player.sendMessage(ChatColor.YELLOW + "Removed " + value + "from running on login");
						Find = true;
					}
				}
				if (Find == false){
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Cannot find command " + ustrcommand + "to be runned on startup\n"+
									   "Please use /onstartcommands to find the commands you have logged");
					
					
				}
			}
				
		}
		
		
		return false;
	}
}

