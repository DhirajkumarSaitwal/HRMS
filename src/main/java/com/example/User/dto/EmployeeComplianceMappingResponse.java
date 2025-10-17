package com.example.User.dto;

import com.example.User.entity.EmployeeComplianceMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeComplianceMappingResponse {
    private Long complianceMappingId;
    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeComplianceMapping.Status status;
    private String remarks;
    private LocalDateTime createdAt;
    private String createdBy;

    // Convert Entity to Response DTO
    public static EmployeeComplianceMappingResponse fromEntity(EmployeeComplianceMapping entity) {
        return EmployeeComplianceMappingResponse.builder()
                .complianceMappingId(entity.getComplianceMappingId())
                .employeeId(entity.getEmployeeId())
                .complianceId(entity.getComplianceId())
                .complianceNumber(entity.getComplianceNumber())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .remarks(entity.getRemarks())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }
}
