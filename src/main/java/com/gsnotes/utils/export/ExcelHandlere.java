package com.gsnotes.utils.export;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandlere {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	
	public  ExcelHandlere() {
		workbook = new XSSFWorkbook();
		
	}

	

	
	public  void export( String sheetName, List<ArrayList<Object>> pDataAndHeader)
			throws ExcelHandlerException {

		
	      sheet = workbook.createSheet(sheetName);
	     // XSSFFont font = workbook.createFont();
			CellStyle style1 = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			sheet.setDefaultColumnWidth(15);
			style1.setAlignment(HorizontalAlignment.CENTER);
			style1.setVerticalAlignment(VerticalAlignment.CENTER);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style3.setAlignment(HorizontalAlignment.CENTER);
			style3.setVerticalAlignment(VerticalAlignment.CENTER);
			style1.setWrapText(false);
			style1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style1.setFillPattern(FillPatternType.BIG_SPOTS);
			style3.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style3.setFillPattern(FillPatternType.BIG_SPOTS);
		int rowNum = 0;

		for (int i=0 ;i<4 ;i++) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : pDataAndHeader.get(i)) {
				Cell cell = row.createCell(colNum++);
				if(i<3)
				cell.setCellStyle(colNum%2==0?style2:style1);
				else
					cell.setCellStyle(style3);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					 //cell.setCellStyle(style1);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					// cell.setCellStyle(style1);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
					 //cell.setCellStyle(style1);
				} else if (field instanceof Long) {
					cell.setCellValue((Long) field);
					// cell.setCellStyle(style1);
				}
			}
		}
		//int rowNum1 = 4;
		for (int i=4 ;i<pDataAndHeader.size() ;i++) {
			
			Row row = sheet.createRow(i);
			int colNum = 0;
			
			for (int j=0 ;j<4;j++)/*Object field : pDataAndHeader.get(i)*/ {
				Cell cell = row.createCell(colNum++);
				 cell.setCellStyle(style2);
				Object field = pDataAndHeader.get(i).get(j);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				} else if (field instanceof Long) {
					cell.setCellValue((Long) field);
				}
			}
			for(int j=0 ;j<sheet.getRow(3).getPhysicalNumberOfCells();j++) {
				Cell cell = row.createCell(colNum++);
				
			}
			//System.out.println( sheet.getRow(i).getCell(4).getReference());
			int index =sheet.getRow(3).getPhysicalNumberOfCells()-2;
			String formule ="AVERAGE(" + sheet.getRow(i).getCell(4).getReference()+":"+sheet.getRow(i).getCell(index-1).getReference()+")";
			String formule1 = "IF(" + sheet.getRow(i).getCell(index).getReference()+ "<" + 12 + ",\"R\",\"V\")";
					 row.getCell(index).setCellFormula(formule);
					 row.getCell(sheet.getRow(3).getPhysicalNumberOfCells()-1).setCellFormula(formule1);
		}

		
	}
		

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	}
	


	
	

