package com.crazypkr.loginandrun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;




public class FileHandler {
	final static String larDataDirPath = "plugins" + File.separator + "LogginAndRun" + File.separator;
	final static String larDataFilePath = larDataDirPath + "lar.dat";
	public static FileHandler fileHandlerInstance = new FileHandler();
	void LoginAndRunSaveData() {
		try{
			new File(larDataDirPath).mkdir();
			FileOutputStream fos = new FileOutputStream(larDataFilePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(LoginAndRun.instance.userCommands);
			oos.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	void LoginAndRunLoadData(){
		
		try{
			FileInputStream fis = new FileInputStream(larDataFilePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object hashmap = ois.readObject();
			LoginAndRun.instance.userCommands = (ConcurrentHashMap<UUID, ArrayList<StoredCommand>>) hashmap;
			ois.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
