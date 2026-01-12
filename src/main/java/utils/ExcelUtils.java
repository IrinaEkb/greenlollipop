package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private ExcelUtils() {}

    // Проверить существует ли Excel файл
    public static boolean isExcelFileExists(String path) {
        File file = new File(path);
        return file.exists() && file.getName().endsWith(".xlsx");
    }

    public static String getCellValue(String path, String sheetName, int rowNum, int colNum) {
        try (FileInputStream file = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            return cell.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + path, e);
        }
    }
}