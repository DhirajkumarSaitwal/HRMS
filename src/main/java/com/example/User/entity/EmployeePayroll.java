package com.example.User.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "employee_payroll")
public class EmployeePayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id", nullable = false)
    private SalaryStructureMaster salaryStructure;

    @Column(nullable = false, length = 20)
    private String month; // e.g. Oct-2025

    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    private String paymentStatus = "Pending";

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    private String bankReferenceNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Column(name = "is_active")
    private Boolean isActive = true;

    private String createdBy;
    private String updatedBy;
}
