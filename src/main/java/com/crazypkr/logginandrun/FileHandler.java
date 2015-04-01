package com.crazypkr.logginandrun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;




public class FileHandler {
	public static FileHandler fileHandlerInstance = new FileHandler();
	private static final String FILENAME = "L_A_R.dat";
	void LogginAndRunSaveData() {
		try{
			
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(LogginAndRun.instance.userCommands);
			oos.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	void LogginAndRunLoadData(){
		
		try{
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object hashmap = ois.readObject();
			LogginAndRun.instance.userCommands = (ConcurrentHashMap<UUID, ArrayList<StoredCommand>>) hashmap;
			ois.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
