package com.BookStore.projectBookStore.services.report;

public interface ReportGenerator {
    void generateReport(String data, String filePath) throws Exception;
}