package com.example.User.controller;

import com.example.User.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private  ReportService reportService;

    @PostMapping("/export")
    @PreAuthorize("hasAnyRole('HR','ADMIN')") // RBAC example — enable method security
    public ResponseEntity<?> exportReport(
            @RequestParam String reportType,
            @RequestParam String exportType
    ) {
        try {
            String filePath = reportService.generateEmployeeReport(reportType, exportType);
            return ResponseEntity.ok().body(
                    new ExportResponse(true, "Report generated successfully", filePath)
            );
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(new ExportResponse(false, iae.getMessage(), null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ExportResponse(false, "Failed to generate report: " + ex.getMessage(), null));
        }
    }

    // simple response payload
    static class ExportResponse {
        private boolean success;
        private String message;
        private String filePath;

        public ExportResponse(boolean success, String message, String filePath) {
            this.success = success;
            this.message = message;
            this.filePath = filePath;
        }
        // getters / setters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getFilePath() { return filePath; }
        public void setSuccess(boolean success) { this.success = success; }
        public void setMessage(String message) { this.message = message; }
        public void setFilePath(String filePath) { this.filePath = filePath; }
    }
}
