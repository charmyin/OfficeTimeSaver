package com.charmyin.staffleave.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.charmyin.staffleave.po.LeavePiece;
import com.charmyin.staffleave.po.LeaveRequest;

public class ExcelSupport {
	
	/**
	 * 
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
			rows.next();
			rows.next();
			while(rows.hasNext()){
				Row row = rows.next();
				Cell cel = row.getCell(0);
				if(cel==null||cel.toString().trim().equals("")){
					break;
				}
				Cell cel7 = row.getCell(7);
				LeaveRequest lq = new LeaveRequest();
				lq.setStaffName(cel.toString().replaceAll("\\s",""));
				lq.setLeavePiece(cel7.toString().replaceAll("\\s",""));
				list.add(lq);
				//System.out.println(cel.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			// TODO: handle exception
		}
		return list;
	}
	
	
	public static void writeToTempleExcel(String tempFilePathWithName, String destFilePathWithoutName, List<LeaveRequest> lq){
		try {
			InputStream inp = new FileInputStream(tempFilePathWithName);
			//InputStream inp = new FileInputStream("workbook.xlsx");
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			inp.close();
			List<String> templeNameList = getTempleExcelNameList(tempFilePathWithName);
			
			for(LeaveRequest lqt : lq){
				int lqIndex =  templeNameList.indexOf(lqt.getStaffName());
				if(lqIndex==0 || lqIndex==-1){
					System.out.println(lqt.getStaffName()+"不存在");
					continue;
				}
				Row row = sheet.getRow(lqIndex+3);
				List<LeavePiece> lpl = lqt.getLeavePiece();
				for(LeavePiece lp : lpl){
					try{
						writeLeavePieceToRow(row,lp);
					}catch(Exception e){
						System.out.println(lqt.getStaffName()+"格式有错误");
						e.printStackTrace();
					}
					
				}
				System.out.println(lqt.getStaffName()+" : "+lqt.getLeavePieceString()+"---(Writed)!");
			}
			
//			Row row = sheet.getRow(2);
//			Cell cell = row.getCell(3);
//			if (cell == null)
//				cell = row.createCell(3);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			cell.setCellValue("a test");
			// Write the output to a file
			//Date date = new Date();
			Calendar cd = Calendar.getInstance();
			 
			FileOutputStream fileOut = new FileOutputStream(destFilePathWithoutName+"\\"+(cd.get(Calendar.MONTH))+"-Report.xlsx");
			wb.write(fileOut);
			fileOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeLeavePieceToRow(Row row, LeavePiece lp) throws Exception {
		Calendar cl = Calendar.getInstance();
		cl.setTime(lp.getStartTime());
		int startDay = cl.get(Calendar.DAY_OF_MONTH);
		//请假类型
		String leaveType = lp.getLeaveType();
		//请假类型符号
		String leaveTypeIcon = LeaveType.props.getProperty(lp.getLeaveType());
		//请假类型在行中的位置
		int cellIndex = 0;
		if(leaveType.endsWith("年假")){
			cellIndex = Integer.parseInt(LeaveType.props.getProperty("年假位置"));
		}else if(LeaveType.props.containsKey(lp.getLeaveType())){
			cellIndex = Integer.parseInt(LeaveType.props.getProperty(lp.getLeaveType()+"位置"));
		}else{
			throw(new Exception("输入的假期有误！请排查！"+lp.getLeaveType()));
		}
		for(int i=0; i<lp.getLeaveDayCount(); i++){
			Cell cell = row.getCell(i+1+startDay);
			String tempCellStr = cell.getStringCellValue();
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(leaveType.endsWith("年假")){
				cell.setCellValue(LeaveType.props.getProperty("年假")+tempCellStr);
			}else{
				cell.setCellValue(leaveTypeIcon+tempCellStr);
			}
		}
		Cell cellIndexCell = row.getCell(cellIndex);
		float cellOldValue = 0f;
		if(cellIndexCell.getCellType() == Cell.CELL_TYPE_NUMERIC){
		 
			cellOldValue = (float)cellIndexCell.getNumericCellValue();
		}
		 
		cellIndexCell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cellIndexCell.setCellValue(lp.getLeaveDayCount()+cellOldValue);
	}


	private static List<String> getTempleExcelNameList(String tempFilePathWithName) {
		List<String> staffNameList = new ArrayList<String>();
		try {
			InputStream inp = new FileInputStream(tempFilePathWithName);
			Workbook wb = WorkbookFactory.create(inp);
			inp.close();
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> it = sheet.iterator();
			it.next();
			it.next();
			it.next();
			while(it.hasNext()){
				Row tempRow = it.next();
				tempRow.getCell(1);
				staffNameList.add(tempRow.getCell(1).getStringCellValue().replaceAll("\\s",""));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return staffNameList;
	}
}
