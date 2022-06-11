package com.gsnotes.utils.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Indexed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * This class implements export and import features from Excel
 * 
 * @author Tarik BOUDAA
 *
 */
public class ExcelHandler {

	
	//private static XSSFWorkbook workbook;
	/** the unique instance of this class */
	private static final ExcelHandler instance = new ExcelHandler();

	/**
	 * private constructor (singleton DP)
	 */
	private ExcelHandler() {
		//this.workbook = new XSSFWorkbook();
	}

	/**
	 * returns the unique instance of this class
	 */
	public static final ExcelHandler getInstance() {
		return instance;
	}

	/**
	 * Exports data to an excel file
	 * @param pFileName the file name
	 * @param sheetName the excel sheet name to export
	 * @param pDataAndHeader the data to export (each line is a list of objects)
	 * @throws ExcelHandlerException
	 */
	public static void export(HttpServletResponse response, String sheetName, List<ArrayList<Object>> pDataAndHeader)
			throws ExcelHandlerException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFFont font = workbook.createFont();
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
		
		for (int i=4 ;i<pDataAndHeader.size() ;i++) {
			
			Row row = sheet.createRow(i);
			int colNum = 0;
			
			for (int j=0 ;j<4;j++) {
				Cell cell = row.createCell(colNum++);
				 cell.setCellStyle(style2);
				Object field = pDataAndHeader.get(i).get(j);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					//cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					//cell.setCellStyle(style);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
					//cell.setCellStyle(style);
				} else if (field instanceof Long) {
					cell.setCellValue((Long) field);
					//cell.setCellStyle(style);
				}
			}
			for(int j=0 ;j<sheet.getRow(3).getPhysicalNumberOfCells();j++) {
				Cell cell = row.createCell(colNum++);
				CellStyle style = workbook.createCellStyle();
				//style.setWrapText(true);
				// font.setFontHeight(12);
				// style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				// style.setAlignment(HorizontalAlignment.CENTER);
				// style.setFont(font);
				
			}
			//System.out.println( sheet.getRow(i).getCell(4).getReference());
			int index =sheet.getRow(3).getPhysicalNumberOfCells()-2;
	String formule ="AVERAGE(" + sheet.getRow(i).getCell(4).getReference()+":"+sheet.getRow(i).getCell(index-1).getReference()+")";
	String formule1 = "IF(" + sheet.getRow(i).getCell(index).getReference()+ "<" + 12 + ",\"R\",\"V\")";
			 row.getCell(index).setCellFormula(formule);
			 row.getCell(sheet.getRow(3).getPhysicalNumberOfCells()-1).setCellFormula(formule1);
		}

		try {
			OutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception ex) {
			throw new ExcelHandlerException("Error while exporting and excel file", ex);
		}
	}
		
	}
	


	
	

