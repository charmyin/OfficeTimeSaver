package com.charmyin.staffleave.util;

import java.io.IOException;
import java.util.Properties;

public class LeaveType {
	
	public static Properties props;
	
	static{
		props = new Properties();
		try {
            //load a properties file from class path, inside static method
 		props.load(LeaveType.class.getClassLoader().getResourceAsStream("leavetype.properties"));
 		//prop.load(new FileInputStream("leavetype.properties"));
            //get the property value and print it out
//        System.out.println(props.getProperty("事假"));
// 		System.out.println(props.getProperty("年假"));
// 		System.out.println(props.getProperty("哺乳假"));

	 	} catch (IOException ex) {
	 		ex.printStackTrace();
	    }
	}
	
}
