package com.example.User.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollDTO {

    private Long payrollId;
    private Long employeeId;
    private Long structureId;

    private String month;
    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    private String paymentStatus;
    private LocalDate paymentDate;
    private String bankReferenceNo;

    private Boolean isActive;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
