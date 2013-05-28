package com.charmyin.staffleave.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.charmyin.staffleave.po.LeaveRequest;

public class ExcelSupport {
	
	/**
	 * 读出excel中的每一条请假记录，添加至list中
	 * @param path
	 * @return
	 */
	public static List<LeaveRequest> readOrignExcelToList(String path){
		
		List<LeaveRequest> list = new ArrayList<LeaveRequest>();
		try {
			//InputStream inp = new FileInputStream("workbook.xls");
			InputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			while(rows.hasNext()){
				Row row = rows.next();
				Cell cel = row.getCell(0);
				if(cel==null){
					System.out.println();
					break;
				}
				Cell cel7 = row.getCell(7);
				LeaveRequest lq = new LeaveRequest();
				lq.setStaffName(cel.toString());
				
				lq.setLeavePiece(cel7.toString());
				System.out.println(cel.toString());
			}
			
			
			
			
//			Cell cell = row.getCell(3);
//			if (cell == null)
//				cell = row.createCell(3);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			cell.setCellValue("a test");
			// Write the output to a file
//			FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//			wb.write(fileOut);
//			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			// TODO: handle exception
		}
		return list;
	}
}
