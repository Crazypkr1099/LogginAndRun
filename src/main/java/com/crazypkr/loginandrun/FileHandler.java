package com.crazypkr.loginandrun;

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
	void LoginAndRunSaveData() {
		try{
			
			FileOutputStream fos = new FileOutputStream(FILENAME);
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
			FileInputStream fis = new FileInputStream(FILENAME);
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
