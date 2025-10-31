package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_payroll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    private Long employeeId;  // FK - employee_master table reference

    private String month; // e.g. Apr-2025

    private Long structureId; // FK - salary_structure_master table reference

    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDate paymentDate;
    private String bankReferenceNo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

