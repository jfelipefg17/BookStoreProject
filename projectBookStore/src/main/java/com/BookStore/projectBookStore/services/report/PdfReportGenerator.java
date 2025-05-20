package com.BookStore.projectBookStore.services.report;

import com.BookStore.projectBookStore.entities.ReportDataDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;

@Component("pdfReportGenerator")
public class PdfReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(ReportDataDTO data, String filePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph("Reporte de Libro"));
        document.add(new Paragraph("Título: " + data.getTitle()));
        document.add(new Paragraph("Autor: " + data.getAuthor()));
        document.add(new Paragraph("Editorial: " + data.getPublisher()));
        document.add(new Paragraph("Categoría: " + data.getCategory()));
        document.add(new Paragraph("Precio: $" + data.getPrice()));
        if (data.getOrder() != null) {
            document.add(new Paragraph("ID Orden: " + data.getOrder().getId()));
            if (data.getOrder().getClient() != null) {
                document.add(new Paragraph("Cliente de la Orden: " + data.getOrder().getClient()));
            }
        }
        // Agrega más campos si los tienes en el DTO
        document.close();
    }
}