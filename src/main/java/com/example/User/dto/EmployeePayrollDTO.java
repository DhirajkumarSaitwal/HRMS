package com.example.User.dto;

import lombok.Data;
import java.util.Date;

@Data
public class EmployeePayrollDTO {

    private Long payrollId;
    private Long employeeId;
    private Long structureId;

    private String month;

    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    private String paymentStatus;
    private Date paymentDate;
    private String bankReferenceNo;
    private Boolean isActive;

    private String createdBy;
    private String updatedBy;
}
