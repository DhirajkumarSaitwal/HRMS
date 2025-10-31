package com.example.User.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank(message = "Month cannot be blank")
    private String month; // Example: Apr-2025

    @NotNull(message = "Structure ID is required")
    private Long structureId;

    @DecimalMin(value = "0.0", message = "Gross salary cannot be negative")
    private double grossSalary;

    @DecimalMin(value = "0.0", message = "Total deductions cannot be negative")
    private double totalDeductions;

    @DecimalMin(value = "0.0", message = "Net salary cannot be negative")
    private double netSalary;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus; // Pending / Processed / Failed

    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @Size(max = 100, message = "Bank Reference No cannot exceed 100 characters")
    private String bankReferenceNo;

    private String createdBy;
}