package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.services.report.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportGenerator pdfReportGenerator;
    private final ReportGenerator excelReportGenerator;

    @Autowired
    public ReportService(
        @Qualifier("pdfReportGenerator") ReportGenerator pdfReportGenerator,
        @Qualifier("excelReportGenerator") ReportGenerator excelReportGenerator
    ) {
        this.pdfReportGenerator = pdfReportGenerator;
        this.excelReportGenerator = excelReportGenerator;
    }

    public void exportReport(String type, String data, String filePath) throws Exception {
        if ("excel".equalsIgnoreCase(type)) {
            excelReportGenerator.generateReport(data, filePath);
        } else if ("pdf".equalsIgnoreCase(type)) {
            pdfReportGenerator.generateReport(data, filePath);
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado: " + type);
        }
    }
}
