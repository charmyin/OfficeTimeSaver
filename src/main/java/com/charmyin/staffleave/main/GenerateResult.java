package com.charmyin.staffleave.main;

import com.charmyin.staffleave.util.ExcelSupport;

public class GenerateResult {
	public static void main(String[] args){
		ExcelSupport.readOrignExcelToList("F:\\zebone\\staffleave\\test_origin.xlsx");
	}
}
