package com.example.User.dto;

import com.example.User.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollDTO {

    private Long payrollId;
    private Long employeeId;
    private String month;
    private Long structureId;
    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
    private String bankReferenceNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

