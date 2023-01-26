package com.studiorg.qa.selenium.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.studiorg.qa.selenium.config.Constants;
import com.studiorg.qa.selenium.executionEngine.DriverScript;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

	// This method is to set the File path and to open the Excel file
	// Pass Excel Path and SheetName as Arguments to this method
	public static void setExcelFile(String Path) throws Exception {
		FileInputStream ExcelFile = new FileInputStream(Path);
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		// ExcelWSheet = ExcelWBook.getSheet(SheetName);
	}

	// This method is to read the test data from the Excel cell
	// In this we are passing parameters/arguments as Row Num and Col Num
	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try{
			String CellData="";
			DataFormatter formatter = new DataFormatter();
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			XSSFCell cell= ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
				XSSFFormulaEvaluator.evaluateAllFormulaCells(ExcelWBook);
				switch (cell.getCachedFormulaResultType()) {
                case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString();
                case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                    return String.format("%f", cell.getNumericCellValue());
                default:
                	System.out.println("No Value Error");
                    break;
				}
			}
			else
				//String CellData1 = Cell.getStringCellValue();				
				CellData=formatter.formatCellValue(cell);
			return CellData;
		 }catch (Exception e){
			 Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
			 DriverScript.bResult = false;
			 return"";
			 }
		
		 }
		 

	// This method is to get the row count used of the excel sheet
	public static int getRowCount(String SheetName) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum() + 1;
		return number;
	}

	// This method is to get the Row number of the test case
	// This methods takes three arguments(Test Case name , Column Number & Sheet
	// name)
	public static int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int i;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int rowCount = ExcelUtils.getRowCount(SheetName);
		for (i = 0; i < rowCount; i++) {
			if (ExcelUtils.getCellData(i, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
				break;
			}
		}
		return i;
	}

	// This method is to get the count of the test steps of test case
	// This method takes three arguments (Sheet name, Test Case Id & Test case
	// row number)
	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		for (int i = iTestCaseStart; i <= ExcelUtils.getRowCount(SheetName); i++) {
			if (!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))) {
				int number = i;
				return number;
			}
		}
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum() + 1;
		return number;
	}

	public static void setCellData(String Result, int RowNum, int ColNum, String SheetName, String FilePath)
			throws Exception {
		try{
			 
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   Row  = ExcelWSheet.getRow(RowNum);
			   Cell = Row.getCell(ColNum);
			   if (Cell == null) {
				   Cell = Row.createCell(ColNum);
				   Cell.setCellValue(Result);
			   } else {
				   Cell.setCellValue(Result);
					}
				// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(FilePath);
				ExcelWBook.write(fileOut);
				//fileOut.flush();
				fileOut.close();
				ExcelWBook = new XSSFWorkbook(new FileInputStream(FilePath));
			 }catch(Exception e){
				Log.error("Class Utils | Method setCellData | Exception desc : "+e.getMessage());
				DriverScript.bResult = false;
				}
			}

}
