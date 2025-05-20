package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.services.ReportService;
import com.BookStore.projectBookStore.entities.ReportDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadReport(
            @RequestParam String type,
            @RequestBody ReportDataDTO data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String filename;
            String contentType;

            if ("pdf".equalsIgnoreCase(type)) {
                reportService.exportReportToStream("pdf", data, baos);
                filename = "reporte.pdf";
                contentType = "application/pdf";
            } else if ("excel".equalsIgnoreCase(type) || "xlsx".equalsIgnoreCase(type)) {
                reportService.exportReportToStream("excel", data, baos);
                filename = "reporte.xlsx";
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            } else {
                return ResponseEntity.badRequest().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
