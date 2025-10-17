package com.example.User.dto;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeComplianceMappingDTO {
    private Long complianceMappingId;
    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String remarks;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
