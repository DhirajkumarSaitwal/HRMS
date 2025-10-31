package com.example.User.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSalaryStructureRequest {

    @NotBlank(message = "structureName is required")
    @Size(max = 100)
    private String structureName;

    @NotNull(message = "basicPay is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal basicPay;

    @NotNull(message = "hra is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal hra;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal variablePay;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal otherAllowances;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal deductions;
    private String updatedBy;


}

