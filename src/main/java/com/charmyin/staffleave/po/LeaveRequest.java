package com.charmyin.staffleave.po;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequest {
	private String staffName;
	
	private List<LeavePiece> leavePiece;
	
	private float leaveDayCount = 0.0f;
	
	 
	public float getLeaveDayCount() {
		if(leaveDayCount != 0.0f ){
			return leaveDayCount;
		}
		for(LeavePiece lp : leavePiece){
			this.leaveDayCount += lp.getLeaveDayCount();
		}
		return leaveDayCount;
	}
	public void setLeaveDayCount(float leaveDayCount) {
		this.leaveDayCount = leaveDayCount;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
 
	public List getLeavePiece() {
		return leavePiece;
	}
	public void setLeavePiece(List leavePiece) {
		this.leavePiece = leavePiece;
	}
	public void setLeavePiece(String string) {
		// TODO Auto-generated method stub
		String[] leavePieces = string.split("\\+");
		this.leavePiece = new ArrayList<LeavePiece>();
		for(String str : leavePieces){
			LeavePiece lp = new LeavePiece(str);
			this.leavePiece.add(lp);
		}
		
	}
}
