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
		FileHandler.fileHandlerInstance.LogginAndRunLoadData();
		instance=this;
		
		// Registers ability to use Permission
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.eventListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) (sender); // Initialize player method as sender
		ArrayList<String> usersCArray = userCommands.get(player.getUniqueId()); // Creates an Array to store Users Commands
		String uStrCommand = ""; // Initializes a variable to store commands users write
		String onOff = null; // Initializes a variable to check if command is on or off
		if (sender.hasPermission("logginandrun.manager")){ // If has perms...
			if (cmd.getName().equalsIgnoreCase("onstart")){ 
				for (int i = 0; i < args.length-1; i++){ // Grabs the command without the [on/off] at the end
					uStrCommand += args[i] + " ";
				}
				
				if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")){ // if equals /os on or off
					if (usersCArray != null){ // If Array Exists
						ArrayList<String> temp = new ArrayList<String>(); 
						for (String s : usersCArray){ // Store all data in a temporary ArrayList
							temp.add(s);
						}
						Iterator<String> ite = usersCArray.iterator();
						while(ite.hasNext()){
							@SuppressWarnings("unused")
							String s = ite.next();
							ite.remove();							
						}
						for (String s: temp){
							player.sendMessage("hi");
							uStrCommand = "";
							String[] splitString = s.split(" ");
							for (int i = 0; i < splitString.length-1; i++){
								uStrCommand += splitString[i] + " ";
							}
							player.sendMessage(splitString[splitString.length-1]);
							if (args[0].contains("on")){
								usersCArray.add(uStrCommand + "on");
							}
							else if (args[0].contains("off")){
								usersCArray.add(uStrCommand + "off");
							}
						}
						temp.removeAll(temp);
						FileHandler.fileHandlerInstance.LogginAndRunSaveData();
					}
					return false;
				}
				if (args[args.length-1].equalsIgnoreCase("on")){
					onOff = "on";
				}
				else if (args[args.length-1].equalsIgnoreCase("off")){
					onOff = "off";
				}
				else{
					onOff = null;
				}
				
				if (onOff == null){
					player.sendMessage(ChatColor.RED + "Sorry, please use /onstart <cmd> <on/off> to toggle commands");
				}
				else{
					if (usersCArray == null){
						usersCArray = new ArrayList<String>();
						userCommands.put(player.getUniqueId(),usersCArray);
					}
					boolean check = true;
					
					for (String s : usersCArray){
						if (s.contains(uStrCommand)){
							String[] splitString = s.split(" ");
							player.sendMessage(onOff + " " + splitString);
							player.sendMessage(onOff.length() + " " + splitString[splitString.length-1].length());
							if (onOff.contains(splitString[splitString.length-1])){
								player.sendMessage(ChatColor.RED + "Sorry, you already have this toggled on or off!");
								check = false;
							}
						}
					}
					if (check == true){
						if (onOff.equals("on")){
							usersCArray.remove(uStrCommand + "off");
			
							player.sendMessage(ChatColor.RED + "Toggled " + uStrCommand + "to run on login");
							usersCArray.add(uStrCommand + "on");
							userCommands.put(player.getUniqueId(),usersCArray);
						}
						else if (onOff.equals("off")){
							usersCArray.remove(uStrCommand + "on");
							
							player.sendMessage(ChatColor.RED + uStrCommand + "will not be ran on login");
							usersCArray.add(uStrCommand + "off");
							userCommands.put(player.getUniqueId(),usersCArray);
							
						}
						FileHandler.fileHandlerInstance.LogginAndRunSaveData();
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("onstartcommands")){
			ArrayList<String> commandList = userCommands.get(player.getUniqueId());
			if (commandList == null || commandList.size() == 0){
				player.sendMessage(ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n" +
						ChatColor.RED + "Theres no commands being\n"	+
						"ran on login\n"		+
						ChatColor.GREEN + "Use /setonstart <cmd>\n" +
						"to add a command\n" + 
						ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n");
				
				}
			
		
			else{
				String listOfCmd = "";
				for (String s:commandList){
					listOfCmd += s + "\n";
				}
				player.sendMessage(ChatColor.YELLOW + "+-+-+-+-+-+-+-+-+-+-+-+\n" +
									"Loggin and Run Commands\n"	+
									"Scheduled to be on\n"		+
									"Login\n"					+
									"+-+-+-+-+-+-+-+-+-+-+-+\n"	+ ChatColor.GREEN + ChatColor.BOLD +
									listOfCmd + "\n");
			}
		}
		
		
		
		
		
		return false;
	}
}
					
