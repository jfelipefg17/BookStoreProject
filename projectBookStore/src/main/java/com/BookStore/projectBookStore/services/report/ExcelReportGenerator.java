package com.BookStore.projectBookStore.services.report;

import com.BookStore.projectBookStore.entities.ReportDataDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Component("excelReportGenerator")
public class ExcelReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(ReportDataDTO data, OutputStream outputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Título");
        header.createCell(1).setCellValue("Autor");
        header.createCell(2).setCellValue("Editorial");
        header.createCell(3).setCellValue("Categoría");
        header.createCell(4).setCellValue("Precio");
        header.createCell(6).setCellValue("ID Orden");
        header.createCell(7).setCellValue("Cliente Orden");

        // Datos
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(data.getTitle());
        row.createCell(1).setCellValue(data.getAuthor());
        row.createCell(2).setCellValue(data.getPublisher());
        row.createCell(3).setCellValue(data.getCategory());
        row.createCell(4).setCellValue(data.getPrice());
        row.createCell(6).setCellValue(data.getOrder() != null ? data.getOrder().getId() : 0);
        row.createCell(7).setCellValue(
            data.getOrder() != null && data.getOrder().getClient() != null
                ? data.getOrder().getClient()
                : ""
        );

        // Ajustar tamaño de columnas
        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
