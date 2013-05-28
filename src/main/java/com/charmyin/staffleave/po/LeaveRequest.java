package com.charmyin.staffleave.po;

import java.util.List;

public class LeaveRequest {
	//请假人名称
	private String staffName;
	
	//请假时间，事由，类型清单
	private List<LeavePiece> leavePiece;
	
	
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
		String[] leavePieces = string.split("+");
		for(String str : leavePieces){
			LeavePiece lp = new LeavePiece(str);
			
		}
	}
}
