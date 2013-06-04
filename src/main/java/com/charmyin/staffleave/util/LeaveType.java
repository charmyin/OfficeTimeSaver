package com.charmyin.staffleave.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LeaveType {
	
	public static Properties props;
	
	static{
		props = new Properties();
		try {
            //load a properties file from class path, inside static method
			 
			props.loadFromXML(LeaveType.class.getClassLoader().getResourceAsStream("leavetype.xml"));

	 	} catch (IOException ex) {
	 		ex.printStackTrace();
	    }
	}
	
}
