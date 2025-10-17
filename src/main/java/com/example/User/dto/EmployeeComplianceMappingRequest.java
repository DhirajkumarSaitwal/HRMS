package com.example.User.dto;

import com.example.User.entity.Status;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EmployeeComplianceMappingRequest {
    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;   // Active / Inactive
    private String remarks;
    private String createdBy;
}
