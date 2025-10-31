package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureDto {
    private Long structureId;
    private String structureName;
    private BigDecimal basicPay;
    private BigDecimal hra;
    private BigDecimal variablePay;
    private BigDecimal otherAllowances;
    private BigDecimal deductions;
    private String createdBy;
    private String updatedBy;
}
