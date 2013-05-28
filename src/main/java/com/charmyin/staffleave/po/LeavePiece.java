package com.charmyin.staffleave.po;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LeavePiece {
	
	private String LeaveType;
	//���ԭ��������ͣ������٣��¼�.....����
	//��ʼʱ��
	private Date startTime;
	//����ʱ��
	private Date endTime;
	
	public LeavePiece(String str) {
		String[] strlps = str.split("=");
		String start = strlps[1].split("-")[0];
		String end = strlps[1].split("-")[1];
		SimpleDateFormat format = new SimpleDateFormat("MM.dd.HH");
		try {
			this.startTime = format.parse(start);
			this.endTime = format.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getLeaveType() {
		return LeaveType;
	}
	public void setLeaveType(String leaveType) {
		LeaveType = leaveType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
