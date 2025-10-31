package com.example.User.dto;

public class SalaryStructureDTO {

    private Long structureId;
    private String structureName;
    private Double basicPay;
    private Double hra;
    private Double variablePay;
    private Double otherAllowances;
    private Double deductions;

    // Getters and Setters
    public Long getStructureId() { return structureId; }
    public void setStructureId(Long structureId) { this.structureId = structureId; }

    public String getStructureName() { return structureName; }
    public void setStructureName(String structureName) { this.structureName = structureName; }

    public Double getBasicPay() { return basicPay; }
    public void setBasicPay(Double basicPay) { this.basicPay = basicPay; }

    public Double getHra() { return hra; }
    public void setHra(Double hra) { this.hra = hra; }

    public Double getVariablePay() { return variablePay; }
    public void setVariablePay(Double variablePay) { this.variablePay = variablePay; }

    public Double getOtherAllowances() { return otherAllowances; }
    public void setOtherAllowances(Double otherAllowances) { this.otherAllowances = otherAllowances; }

    public Double getDeductions() { return deductions; }
    public void setDeductions(Double deductions) { this.deductions = deductions; }
}
