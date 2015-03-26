package com.crazypkr.logginandrun;
import java.util.ArrayList;
import java.util.Iterator;
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
		

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) (sender);
		ArrayList<String> usersCArray = userCommands.get(player.getUniqueId());
		String ustrcommand = "";
		String onoff = null;
		if (sender.hasPermission("logginandrun.manager")){
			if (cmd.getName().equalsIgnoreCase("onstart")){
				for (int i = 0; i < args.length-1; i++){
					ustrcommand += args[i] + " ";
				}
				
				if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")){
					if (usersCArray != null){
						ArrayList<String> temp = new ArrayList<String>();
						for (String s : usersCArray){
							temp.add(s);
						}
						Iterator<String> ite = usersCArray.iterator();
						while(ite.hasNext()){
							String s = ite.next();
							ite.remove();							
						}
						for (String s: temp){
							player.sendMessage("hi");
							ustrcommand = "";
							String[] sstring = s.split(" ");
							for (int i = 0; i < sstring.length-1; i++){
								ustrcommand += sstring[i] + " ";
							}
							player.sendMessage(sstring[sstring.length-1]);
							if (args[0].contains("on")){
								usersCArray.add(ustrcommand + "on");
							}
							else if (args[0].contains("off")){
								usersCArray.add(ustrcommand + "off");
							}
						}
						temp.removeAll(temp);
					}
					return false;
				}
				if (args[args.length-1].equalsIgnoreCase("on")){
					onoff = "on";
				}
				else if (args[args.length-1].equalsIgnoreCase("off")){
					onoff = "off";
				}
				else{
					onoff = null;
				}
				
				if (onoff == null){
					player.sendMessage(ChatColor.RED + "Sorry, please use /onstart <cmd> <on/off> to toggle commands");
				}
				else{
					if (usersCArray == null){
						usersCArray = new ArrayList<String>();
						userCommands.put(player.getUniqueId(),usersCArray);
					}
					boolean check = true;
					for (String s : usersCArray){
						if (s.contains(ustrcommand)){
							String[] sstring = s.split(" ");
							player.sendMessage(onoff + " " + sstring);
							player.sendMessage(onoff.length() + " " + sstring[sstring.length-1].length());
							if (onoff.contains(sstring[sstring.length-1])){
								player.sendMessage(ChatColor.RED + "Sorry, you already have this toggled on or off!");
								check = false;
							}
						}
					}
					if (check == true){
						if (onoff.equals("on")){
							usersCArray.remove(ustrcommand + "off");
			
							player.sendMessage(ChatColor.RED + "Toggled " + ustrcommand + "to run on login");
							usersCArray.add(ustrcommand + "on");
							userCommands.put(player.getUniqueId(),usersCArray);
						}
						else if (onoff.equals("off")){
							usersCArray.remove(ustrcommand + "on");
							
							player.sendMessage(ChatColor.RED + ustrcommand + "will not be ran on login");
							usersCArray.add(ustrcommand + "off");
							userCommands.put(player.getUniqueId(),usersCArray);
						}
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("onstartcommands")){
			ArrayList<String> commandlist = userCommands.get(player.getUniqueId());
			if (commandlist == null || commandlist.size() == 0){
				player.sendMessage(ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n" +
						ChatColor.RED + "Theres no commands being\n"	+
						"ran on login\n"		+
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
									"+-+-+-+-+-+-+-+-+-+-+-+\n"	+ ChatColor.GREEN + ChatColor.BOLD +
									listofcmd + "\n");
			}
		}
		
		
		
		
		
		return false;
	}
}
					
