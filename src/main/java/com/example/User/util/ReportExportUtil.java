package com.example.User.util;

import com.example.User.dto.EmployeeDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReportExportUtil {
    private static final String[] HEADERS = {
            "Employee ID", "First Name", "Last Name", "Department ID", "Email", "status", "Created At", "Updated At"
    };

    public static void exportToExcel(List<EmployeeDTO> employees, Path filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employee Master");

            // header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont(); // ✅ Correct Font import (not java.awt)
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(HEADERS[i]);
                c.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (EmployeeDTO e : employees) {
                Row r = sheet.createRow(rowIdx++);
                r.createCell(0).setCellValue(e.getEmployeeId() == null ? "" : String.valueOf(e.getEmployeeId()));
                r.createCell(1).setCellValue(StringUtils.hasText(e.getFirstName()) ? e.getFirstName() : "");
                r.createCell(2).setCellValue(StringUtils.hasText(e.getLastName()) ? e.getLastName() : "");
                r.createCell(3).setCellValue(e.getDepartment() == null ? "" : e.getDepartment());
                r.createCell(4).setCellValue(StringUtils.hasText(e.getEmail()) ? e.getEmail() : "");
                //r.createCell(5).setCellValue(Boolean.TRUE.equals(e.getStatus()) ? "Active" : "Inactive");
                r.createCell(5).setCellValue(StringUtils.hasText(e.getStatus()) ? e.getStatus(): "");
                r.createCell(6).setCellValue(e.getCreatedAt() == null ? "" : e.getCreatedAt().toString());
                r.createCell(7).setCellValue(e.getUpdatedAt() == null ? "" : e.getUpdatedAt().toString());
            }

            // autosize columns
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // ensure directories exist
            Files.createDirectories(filePath.getParent());
            try (OutputStream os = Files.newOutputStream(filePath)) {
                workbook.write(os);
            }
        }
    }

    public static void exportToCsv(List<EmployeeDTO> employees, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (EmployeeDTO e : employees) {
                csvPrinter.printRecord(
                        e.getEmployeeId(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment(),
                        e.getEmail(),
                        //Boolean.TRUE.equals(e.getStatus()) ? "Active" : "Inactive",
                        e.getStatus(),
                        e.getCreatedAt(),
                        e.getUpdatedAt()
                );
            }
            csvPrinter.flush();
        }
    }
}
