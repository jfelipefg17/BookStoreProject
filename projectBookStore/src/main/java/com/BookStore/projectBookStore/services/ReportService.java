package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.services.report.ReportGenerator;
import com.BookStore.projectBookStore.services.report.PdfReportGenerator;
import com.BookStore.projectBookStore.services.report.ExcelReportGenerator;
import com.BookStore.projectBookStore.entities.ReportDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private PdfReportGenerator pdfReportGenerator;

    @Autowired
    private ExcelReportGenerator excelReportGenerator;

    public void exportReport(String type, ReportDataDTO data, String filePath) throws Exception {
        if ("pdf".equalsIgnoreCase(type)) {
            pdfReportGenerator.generateReport(data, filePath);
        } else if ("excel".equalsIgnoreCase(type) || "xlsx".equalsIgnoreCase(type)) {
            excelReportGenerator.generateReport(data, filePath);
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado: " + type);
        }
    }
}
