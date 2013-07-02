package com.charmyin.attendence.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.charmyin.attendence.po.Attendence;
import com.charmyin.attendence.po.Staff;

public class GenerateAttendenceList {
	//离职人员打卡数据
	public static List<Attendence> atNotfoundList;
	private static String saturday = "29";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//离职人员打卡数据
		atNotfoundList = new ArrayList<Attendence>();
		
		// TODO Auto-generated method stub
		//员工列表
		HashMap<String,Staff> staffMap = convertStaffPropertiesToList("src/main/java/com/charmyin/attendence/staffcode.properties");
		
		
		//遍历所有的考勤数据，将每条数据录入员工map中对象的list
		int countAll = readAttendenceToStaffMap(staffMap);
		
		//获取最终形式
		
		
		
		System.out.println("共有"+countAll+"条数据，有"+atNotfoundList.size()+"条记录无对应人员");
	}
	
	//遍历所有的考勤数据，将每条数据录入员工map中对象的list
	public static int readAttendenceToStaffMap(HashMap<String,Staff> staffMap){
		BufferedReader br = null;
		int count = 0;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("F:\\zebone\\staffattendence\\24-29.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] str = sCurrentLine.split("\\s+");
				System.out.println(str[1]+"~~"+str[2]+"~~"+str[3]);
				String[] tempTime = str[3].split(":");
				int hourOfDay = Integer.parseInt(tempTime[0]);
				int minuteOfHour = Integer.parseInt(tempTime[1]);
				Attendence atd = new Attendence();
				atd.setThatDay(str[2]);
				atd.setComeLeaveTimeOfDay(str[3]);
				
				//在上班时间打卡的行为都不是好行为~
				if(saturday.equals(str[2].split("-")[2])){
					System.out.println("周六打卡情况："+str[1]+"~~"+str[2]+"~~"+str[3]);
					if((hourOfDay>=8 && hourOfDay<11)){
						if((hourOfDay==8&&minuteOfHour==0)){
							atd.setWrong(false);
						}else{
							atd.setWrong(true);
						}
					}else if(hourOfDay==11&&minuteOfHour<15){
						//11.15前离开算早退
						atd.setWrong(true);
					}else{
						atd.setWrong(false);
					}
				}else{
					//正常工作日打卡
					if((hourOfDay>=8 && hourOfDay<17)){
						if((hourOfDay==8&&minuteOfHour==0)){
							atd.setWrong(false);
						}else{
							atd.setWrong(true);
						}
					}else{
						//五点整打卡不算早退
//						if(hourOfDay==17&&minuteOfHour==0){
//							atd.setWrong(true);
//						}
						atd.setWrong(false);
					}
				}
				
				count++;
				//如果剔除离职的id
				if(!staffMap.containsKey(str[1])){
					 atNotfoundList.add(atd);
					 continue;
				}
				HashMap<String, String> idtimesmap = staffMap.get(str[1]).getIdTimes();
				
				String tempStrToAdd = str[3];
				if(atd.isWrong()){
					tempStrToAdd = "**"+str[3]+"**";	
				}
				if(idtimesmap.containsKey(str[2])){
					String tempStr = idtimesmap.get(str[2])+","+tempStrToAdd;			
					idtimesmap.put(str[1], tempStr);
				}else{
					idtimesmap.put(str[1], tempStrToAdd);
				}
				//staffMap.get(str[1]).getIdTimes().put("", "");
				staffMap.get(str[1]).getList().add(atd);
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
		System.out.println("共有"+count+"考勤条记录!");
		return count;
	}
	
	
	//获取所有人员对象
	public static HashMap<String,Staff> convertStaffPropertiesToList(String path){
		
		HashMap<String,Staff> hm = new HashMap<String,Staff>();
		
		Properties prop = new Properties();
		 try{
			 prop.load(new FileInputStream(path));
		 
			 Set<Object> setList = prop.keySet();
			 Iterator it = setList.iterator();
			 String id = "";
			 while(it.hasNext()){
				 id = it.next().toString();
				 Staff ts = new Staff();
				 ts.setId(id);
				 String tdn = new String(prop.getProperty(id).getBytes("ISO8859-1"), "UTF-8");
				 // System.out.println(tdn);
				 String[] array = tdn.split("\\+");
				 ts.setDepartment(array[0]);
				 ts.setName(array[1]);
				 hm.put(id.toString(), ts);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		System.out.println("共有人数:"+hm.size());
		return hm;
	}

}
