package com.crazypkr.loginandrun;

import java.io.Serializable;

class StoredCommand implements Serializable {
	private static final long serialVersionUID = 1923748312392374231L;
	String strCommand;
	boolean enabled;

	public StoredCommand(String strCommand, boolean enabled){
		this.strCommand = strCommand;
		this.enabled = enabled;
	}
}
	
	
	
	
	
	
	
	
	

