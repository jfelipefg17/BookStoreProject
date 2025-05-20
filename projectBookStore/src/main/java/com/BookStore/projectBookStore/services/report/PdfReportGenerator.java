package com.BookStore.projectBookStore.services.report;

import com.BookStore.projectBookStore.entities.ReportDataDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.io.OutputStream;

@Component("pdfReportGenerator")
public class PdfReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(ReportDataDTO data, OutputStream outputStream) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph("Reporte de Libro"));
        document.add(new Paragraph("Título: " + data.getTitle()));
        document.add(new Paragraph("Autor: " + data.getAuthor()));
        document.add(new Paragraph("Editorial: " + data.getPublisher()));
        document.add(new Paragraph("Categoría: " + data.getCategory()));
        document.add(new Paragraph("Precio: $" + data.getPrice()));
        if (data.getPedidoId() != null) {
            document.add(new Paragraph("ID Pedido: " + data.getPedidoId()));
            document.add(new Paragraph("Usuario Pedido: " + data.getUsuarioPedido()));
            document.add(new Paragraph("Pago: " + data.getPagoPedido()));
            document.add(new Paragraph("Descuento: " + (data.isDescuentoPedido() ? "Sí" : "No")));
        }
        // Agrega más campos si los tienes en el DTO
        document.close();
    }
}