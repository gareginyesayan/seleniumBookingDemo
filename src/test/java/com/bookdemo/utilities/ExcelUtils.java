package com.bookdemo.utilities;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelUtils {

    public String path = System.getProperty("user.dir")+"/src/test/resources/TestData/Data.xlsx";
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

//    public static void main(String[] args){
//        System.out.println(getCellDataString("Sheet1",0, 0));
//        System.out.println(getCellDataNumber(2,0));
//    }

    public ExcelUtils() {
        this.path = path;
        try {
            workbook = new XSSFWorkbook(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelUtils(String anotherPath) {
        this.path = anotherPath;
        try {
            workbook = new XSSFWorkbook(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getRowCount(String sheetName){
        try {
//            workbook = new XSSFWorkbook(path);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int rowCount = sheet.getPhysicalNumberOfRows();
        return rowCount;
    }

    public  String getCellDataString(String sheetName, int rowNum, int colNum){
        try {
//            workbook = new XSSFWorkbook(path);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cellType = sheet.getRow(rowNum).getCell(colNum).getCellType().name();
        String cellValue ="-1";
        if(cellType.equals("STRING")) {

            cellValue = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();

        } else if(cellType.equals("NUMERIC")) {

            cellValue = String.valueOf((int)sheet.getRow(rowNum).getCell(colNum).getNumericCellValue());

        } else System.out.println("Unknown cell type");
        return cellValue;

    }
    public int getCellDataNumber(String sheetName, int rowNum, int colNum) {
        try {
//            workbook = new XSSFWorkbook(path);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int cellValue = (int) sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
        return cellValue;
    }


}
