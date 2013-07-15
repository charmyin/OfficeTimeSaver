package com.charmyin.attendence.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.charmyin.attendence.po.Attendence;
import com.charmyin.attendence.po.Staff;
import com.charmyin.attendence.po.StaffAttendencePiece;

public class GenerateAttendenceList {
	//离职人员打卡数据
	public static List<Attendence> atNotfoundList;
	private static String saturday = "13";
	private static List<StaffAttendencePiece> sapList = new ArrayList<StaffAttendencePiece>();
	private static String  attendenceFilePath= "F:\\zebone\\staffattendence\\7.8-7.13.txt";
	
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
		Iterator<Entry<String,Staff>> it = staffMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,Staff> tempEntry = it.next();
			Staff sf = tempEntry.getValue();
			Iterator<Entry<String,String>> it1 = sf.getIdTimes().entrySet().iterator();
			while(it1.hasNext()){
				Entry<String,String> tempEntry1 = it1.next();
				StaffAttendencePiece sap = new StaffAttendencePiece();
				sap.setId(sf.getId());
				sap.setDate(tempEntry1.getKey());
				sap.setDepartment(sf.getDepartment());
				sap.setName(sf.getName());
				sap.setRecord(tempEntry1.getValue());
				sapList.add(sap);
			}
			//System.out.println(sf.getId()+"--"+sf.getDepartment());
		}
		System.out.println("<!DOCTYPE html><html><head></head><body><table>");
		for(StaffAttendencePiece ss : sapList){
			System.out.println("<tr><td>"+ss.getDepartment()+"</td><td>"+ss.getId()+"</td><td>"+ss.getName()+"</td><td>"+ss.getDate().trim()+"</td><td>"+ss.getRecord()+"</td></tr>");
		}
		System.out.println("</table></body></html>");
		System.out.println("共有"+countAll+"条数据，有"+atNotfoundList.size()+"条记录无对应人员");
		for(Attendence at : atNotfoundList){
			System.out.println(at.getId());
		}
	}
	
	//遍历所有的考勤数据，将每条数据录入员工map中对象的list
	public static int readAttendenceToStaffMap(HashMap<String,Staff> staffMap){
		BufferedReader br = null;
		int count = 0;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(attendenceFilePath));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] str = sCurrentLine.split("\\s+");
			//	System.out.println(str[1]+"~~"+str[2]+"~~"+str[3]);
				String[] tempTime = str[3].split(":");
				int hourOfDay = Integer.parseInt(tempTime[0]);
				int minuteOfHour = Integer.parseInt(tempTime[1]);
				Attendence atd = new Attendence();
				atd.setId(str[1]);
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
					idtimesmap.put(str[2], tempStr);
				}else{
					idtimesmap.put(str[2], tempStrToAdd);
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
