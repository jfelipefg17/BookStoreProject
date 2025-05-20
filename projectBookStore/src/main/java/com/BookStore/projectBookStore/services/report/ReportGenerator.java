package com.BookStore.projectBookStore.services.report;

import com.BookStore.projectBookStore.entities.ReportDataDTO;
import java.io.OutputStream;

public interface ReportGenerator {
    void generateReport(ReportDataDTO data, OutputStream outputStream) throws Exception;
}