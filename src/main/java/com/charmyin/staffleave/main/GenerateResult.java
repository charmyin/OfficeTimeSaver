package com.charmyin.staffleave.main;

import java.util.List;

import com.charmyin.staffleave.po.LeaveRequest;
import com.charmyin.staffleave.util.ExcelSupport;

public class GenerateResult {
	public static void main(String[] args){
	 
		List<LeaveRequest> lq = ExcelSupport.readOrignExcelToList("F:\\zebone\\staffleave\\test_origin.xlsx");
		
		ExcelSupport.writeToTempleExcel("F:\\zebone\\staffleave\\temple.xlsx", "F:\\zebone\\staffleave\\", lq);
	}
}
