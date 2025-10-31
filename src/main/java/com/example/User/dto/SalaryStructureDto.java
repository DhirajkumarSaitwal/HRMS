package com.example.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryStructureDto {
    private Long structureId;
    private String structureName;
    private Double basicPay;
    private Double hra;
    private Double variablePay;
    private Double otherAllowances;
    private Double deductions;
    private String createdBy;
    private String updatedBy;


}

