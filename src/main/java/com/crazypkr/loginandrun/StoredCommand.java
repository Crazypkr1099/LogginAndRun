package com.crazypkr.loginandrun;

import java.io.Serializable;

public class StoredCommand implements Serializable {
	
	private static final long serialVersionUID = 1923748312392374231L;
	String Command;
	boolean enabled;
	

	StoredCommand(String command, boolean enabled){
		this.Command = command;
		this.enabled = enabled;
		
	}
	
	public String getStatus(){
		
		if(this.enabled){
			return "Enabled";
		}
		else{
			return "Disabled";
		}
	}
		
		
		
}
	
	
	
	
	
	
	
	
	
	

