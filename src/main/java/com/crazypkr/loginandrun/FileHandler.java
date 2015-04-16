package com.crazypkr.loginandrun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class FileHandler { // Handles files...
	final static String larDataDirPath = "plugins" + File.separator + "LoginAndRun" + File.separator;
	final static String larDataFilePath = larDataDirPath + "lar.dat";
	public static FileHandler fileHandlerInstance = new FileHandler();
	void LoginAndRunSaveData() {
		try {
			new File(larDataDirPath).mkdir(); // Make a directory
			FileOutputStream fos = new FileOutputStream(larDataFilePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(LoginAndRun.instance.usersCommands); // Write to file userCommands
			oos.close(); // Close the ObjectOutputStream
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	void LoginAndRunLoadData(){
		try {
			FileInputStream fis = new FileInputStream(larDataFilePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object hashmap = ois.readObject(); // Read from file
			LoginAndRun.instance.usersCommands = (ConcurrentHashMap<UUID, ArrayList<StoredCommand>>) hashmap; // Add file data to userCommands
			ois.close(); // Close the ObjectInputStream
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
