package com.charmyin.attendence.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.charmyin.attendence.po.Staff;

public class FindSomeOne {
	private static String id = "337";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br = null;
		// TODO Auto-generated method stub
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("F:\\zebone\\staffattendence\\五楼.dat"));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] str = sCurrentLine.split("\\s+");
				if(str[1].equals(id)){
					String[] temp = str[3].split(":");
					if(temp[0].equals("08")&&temp[1].equals("00"))
					System.out.println(str[1]+"~~"+str[2]+"~~"+str[3]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("共有"+""+"考勤条记录!");
	}
	
	
	

}
