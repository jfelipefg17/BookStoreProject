package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.services.report.PdfReportGenerator;
import com.BookStore.projectBookStore.services.report.ExcelReportGenerator;
import com.BookStore.projectBookStore.entities.ReportDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.FileOutputStream;

@Service
public class ReportService {

    @Autowired
    private PdfReportGenerator pdfReportGenerator;

    @Autowired
    private ExcelReportGenerator excelReportGenerator;

    public void exportReport(String type, ReportDataDTO data, String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            exportReportToStream(type, data, fos);
        }
    }

    public void exportReportToStream(String type, ReportDataDTO data, OutputStream os) throws Exception {
        if ("pdf".equalsIgnoreCase(type)) {
            pdfReportGenerator.generateReport(data, os);
        } else if ("excel".equalsIgnoreCase(type) || "xlsx".equalsIgnoreCase(type)) {
            excelReportGenerator.generateReport(data, os);
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado: " + type);
        }
    }
}
