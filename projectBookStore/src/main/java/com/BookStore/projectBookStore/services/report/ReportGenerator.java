package com.BookStore.projectBookStore.services.report;

import com.BookStore.projectBookStore.entities.ReportDataDTO;

public interface ReportGenerator {
    void generateReport(ReportDataDTO data, String filePath) throws Exception;
}