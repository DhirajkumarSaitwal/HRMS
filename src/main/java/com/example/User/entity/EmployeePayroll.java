package com.example.User.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_payroll", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employeeId", "month"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId ;

    private Long employeeId;
    private String structureId;
    private String month;
    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDate paymentDate;
    private String bankReferenceNo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PaymentStatus {
        PENDING, PROCESSED, FAILED
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        if (paymentStatus == null) paymentStatus = PaymentStatus.PENDING;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

