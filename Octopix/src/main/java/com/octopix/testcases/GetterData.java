package com.octopix.testcases;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetterData {

	public List<FourWheeler> fourWheelerExcelData() {
//	public static void main(String[] args) {

//		ReadConfig config = new ReadConfig();
//		String filePath = config.getExcelPath();
		
		String filePath = "C:\\Users\\Xinthe\\eclipse-workspace\\Octopix\\src\\main\\java\\com\\octopix\\testdata\\Gamdias201223loop.xlsx";

		String sheetName = "ALPR";
		List<FourWheeler> excelDataList = null;
		try {
			excelDataList = readExcel(filePath, sheetName);
			displayExcelData(excelDataList);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelDataList;
		
	}

	public static List<FourWheeler> readExcel(String filePath, String sheetName) throws IOException {
		List<FourWheeler> excelDataList = new ArrayList<>();

		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {

			// Assuming that you are using the first sheet. If you want to read multiple
			// sheets, modify accordingly.
			Sheet sheet = workbook.getSheet(sheetName);

			// Iterate through rows
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();

				FourWheeler excelData = new FourWheeler();

				// Iterate through cells in the row
				int cellIndex = 0;
				while (cellIterator.hasNext()) {
					Cell valueCell = cellIterator.next();
					setCellValue(excelData, cellIndex, getCellValueAsString(valueCell));
					cellIndex++;
				}
				// Store the data in the List
				excelDataList.add(excelData);
			}
		}
		return excelDataList;
	}

	public static void displayExcelData(List<FourWheeler> excelDataList) {
		for (int i = 1; i < excelDataList.size() - 2; i++) {
			FourWheeler excelData = excelDataList.get(i);
			if (excelData.getCounting() != null && excelData.getOCR() != null && excelData.getColor() != null
					&& excelData.getInspection() != null) {
				System.out.println(excelData);
			}			
		}		
	}

	public static void setCellValue(FourWheeler excelData, int cellIndex, String cellValue) {
		switch (cellIndex) {
		case 0:
			excelData.setCounting(cellValue);
			break;
		case 1:
			excelData.setOCR(cellValue);
			break;
		case 2:
			excelData.setColor(cellValue);
			break;
		case 3:
			excelData.setInspection(cellValue);
			break;
		// Add more cases if you have more columns
		}
	}

	public static String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return ""; // Handle blank cells
		default:
			return "Unsupported Cell Type";
		}
	}

}

