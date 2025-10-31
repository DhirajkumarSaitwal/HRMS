package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureResponseDTO {

    private Long structureId;
    private String structureName;
    private BigDecimal basicPay;
    private BigDecimal hra;
    private BigDecimal variablePay;
    private BigDecimal otherAllowances;
    private BigDecimal deductions;
    private BigDecimal grossSalary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
