package com.BookStore.projectBookStore.services.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;

@Component("pdfReportGenerator")
public class PdfReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(String data, String filePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph(data));
        document.close();
    }
}