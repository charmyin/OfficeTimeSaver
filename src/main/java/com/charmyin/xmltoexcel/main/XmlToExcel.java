package com.charmyin.xmltoexcel.main;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToExcel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    try {
	    	
	        Workbook wb = new XSSFWorkbook();  // or new XSSFWorkbook();
	        Sheet sheetContacts = wb.createSheet("Contacts");
	        
	    	
			File fXmlFile = new File("F:\\zebone\\xmltoexcel\\xml.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Contact");
		 
			System.out.println("----------------------------");
		 
			
			
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Row row = sheetContacts.createRow((short)temp);
			    // Create a cell and put a value in it.
			   
				
				
				Node nNode = nList.item(temp);
		 
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					
					//System.out.println("Staff id : " + eElement.getAttribute(""));
					System.out.println("First Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("Starred").item(0).getTextContent());
					
				    Cell cell = row.createCell(0);
				    cell.setCellValue(eElement.getElementsByTagName("Name").item(0).getTextContent());
					    
					String phoneNumber = "";
					NodeList phoneList = eElement.getElementsByTagName("Phone");
					for(int i=0; i<phoneList.getLength(); i++){
						if(i!=0){
							phoneNumber = phoneNumber+",";
						}
						phoneNumber += phoneList.item(i).getTextContent();
						System.out.println(phoneList.item(i).getTextContent());
					}
					Cell cell1 = row.createCell(1);
				    cell1.setCellValue(phoneNumber);
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream("D:\\"+"contacts.xlsx");
			wb.write(fileOut);
			fileOut.close();
			
			
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

}
