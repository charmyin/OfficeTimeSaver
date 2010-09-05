package com.charmyin.staffleave.po;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LeavePiece {
	
	private String leaveType;
	private Date startTime;
	private Date endTime;
	
	private float leaveDayCount;
	
	public float getLeaveDayCount() {
		return leaveDayCount;
	}
	public void setLeaveDayCount(float leaveDayCount) {
		this.leaveDayCount = leaveDayCount;
	}
	public void setLeaveDayCount() throws Exception {
		
		// TODO Auto-generated method stub
		Date startDate = this.startTime;
		Calendar startCd = Calendar.getInstance();
		startCd.setTime(startDate);
		
		Date endDate = this.endTime;
		Calendar endCd = Calendar.getInstance();
		endCd.setTime(endDate);
		
		int startDay = startCd.get(Calendar.DAY_OF_MONTH);
		int startDayHour = startCd.get(Calendar.HOUR_OF_DAY);
		int endDay = endCd.get(Calendar.DAY_OF_MONTH);
		int endDayHour = endCd.get(Calendar.HOUR_OF_DAY);
		
		leaveDayCount = endDay - startDay;
		
		if(startDayHour>=6 && startDayHour<11){
			leaveDayCount+=1.0f;
		}else if(startDayHour>=11 && startDayHour<=15){
			leaveDayCount+=0.5f;
		}else{
			leaveDayCount = 0.0f ;
			throw(new Exception("!"));
		}
		
		if(endDayHour>=11 && endDayHour<=14){
			leaveDayCount-=0.5f;
		}else if(endDayHour>=16 && endDayHour<=18){
			leaveDayCount-=0.0f;
		}else{
			leaveDayCount = 0.0f ;
			throw(new Exception("日期格式有误!"));
		}
		
		//System.out.println(leaveDayCount);
		
		
	}
	
	public LeavePiece(String str) throws Exception{
		String[] strlps = str.split("=");
		String start = strlps[1].split("-")[0];
		String end = strlps[1].split("-")[1];
		this.leaveType = strlps[0];
		SimpleDateFormat format = new SimpleDateFormat("MM.dd.HH");
		try {
			this.startTime = format.parse(start);
			this.endTime = format.parse(end);
			setLeaveDayCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("时间格式有错误");
		}
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) throws Exception {
		this.startTime = startTime;
		if(this.endTime!=null){
			setLeaveDayCount();
		}
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) throws Exception {
		this.endTime = endTime;
		if(this.startTime!=null){
			setLeaveDayCount();
		}
	}

}
