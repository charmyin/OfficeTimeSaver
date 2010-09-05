package com.charmyin.staffleave.main;

import java.util.List;

import com.charmyin.staffleave.po.LeaveRequest;
import com.charmyin.staffleave.util.ExcelSupport;

public class GenerateResult {
	public static void main(String[] args){
	 
		List<LeaveRequest> lq = ExcelSupport.readOrignExcelToList("F:\\zebone\\staffleave\\8month.xlsx");
		
		ExcelSupport.writeToTempleExcel("F:\\zebone\\staffleave\\temple.xlsx", "F:\\zebone\\staffleave\\", lq);
		System.out.println("aaa");
	}
}
