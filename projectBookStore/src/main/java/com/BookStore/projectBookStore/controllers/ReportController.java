package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/generate")
    @ResponseBody
    public String generateReport(
            @RequestParam String type,
            @RequestParam String data,
            @RequestParam String filePath) {
        try {
            reportService.exportReport(type, data, filePath);
            return "Reporte " + type + " generado correctamente en: " + filePath;
        } catch (Exception e) {
            return "Error al generar el reporte: " + e.getMessage();
        }
    }
}
