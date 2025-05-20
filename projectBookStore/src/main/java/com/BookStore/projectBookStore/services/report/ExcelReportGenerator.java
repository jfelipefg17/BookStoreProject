package com.BookStore.projectBookStore.services.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;

@Component("excelReportGenerator")
public class ExcelReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(String data, String filePath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(data);
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
