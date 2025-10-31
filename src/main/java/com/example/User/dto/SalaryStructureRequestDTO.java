package com.example.User.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureRequestDTO {
    @NotNull(message = "Structure name cannot be null")
    @Size(max = 100, message = "Structure name must not exceed 100 characters")
    private String structureName;

    @Min(value = 0, message = "Basic pay cannot be negative")
    private double basicPay;

    @Min(value = 0, message = "HRA cannot be negative")
    private double hra;

    @Min(value = 0, message = "Variable pay cannot be negative")
    private double variablePay;

    @Min(value = 0, message = "Other allowances cannot be negative")
    private double otherAllowances;

    @Min(value = 0, message = "Deductions cannot be negative")
    private double deductions;

}
