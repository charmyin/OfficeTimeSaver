package com.charmyin.attendence.po;


public class Attendence {
	private String id;
	private String thatDay;
	private String comeLeaveTimeOfDay;
	private boolean isWrong;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getThatDay() {
		return thatDay;
	}
	public void setThatDay(String thatDay) {
		this.thatDay = thatDay;
	}
	public String getComeLeaveTimeOfDay() {
		return comeLeaveTimeOfDay;
	}
	public void setComeLeaveTimeOfDay(String comeLeaveTimeOfDay) {
		this.comeLeaveTimeOfDay = comeLeaveTimeOfDay;
	}
	public boolean isWrong() {
		return isWrong;
	}
	public void setWrong(boolean isWrong) {
		this.isWrong = isWrong;
	}
}
