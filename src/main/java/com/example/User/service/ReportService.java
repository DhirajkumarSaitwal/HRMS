package com.example.User.service;

public interface ReportService {
    /**
     * Generate report and return absolute file path
     * @param reportType must be "EmployeeMaster"
     * @param exportType "excel" or "csv"
     * @return Path to generated file as string
     */
    String generateEmployeeReport(String reportType, String exportType) throws Exception;
}
