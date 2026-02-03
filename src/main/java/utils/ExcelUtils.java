package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private ExcelUtils() {}

    public static boolean isExcelFileExists(String path) {
        File file = new File(path);
        return file.exists() && (path.endsWith(".xlsx") || path.endsWith(".xls"));
    }

    public static String getCellValue(String path, String sheetName, int rowNum, int colNum) {
        try (FileInputStream file = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(file)) {  // <- современный способ

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            return cell.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + path, e);
        }
    }

    public static Object[][] getTestData(String path, String sheetName) {
        try (FileInputStream file = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = (cell == null) ? "" : cell.toString();
                }
            }
            return data;

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + path, e);
        }
    }
}