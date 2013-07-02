package com.charmyin.attendence.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Staff {
	private String id;
	private String name;
	private String department;
	List<Attendence> list = new ArrayList<Attendence>();
	private HashMap<String, String> idTimes = new HashMap<String, String>();
	public HashMap<String, String> getIdTimes() {
		return idTimes;
	}
	public void setIdTimes(HashMap<String, String> idTimes) {
		this.idTimes = idTimes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<Attendence> getList() {
		return list;
	}
	public void setList(List<Attendence> list) {
		this.list = list;
	}
}
