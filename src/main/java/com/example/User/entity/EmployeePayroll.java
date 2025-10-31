package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    // ✅ Prevent JSON infinite loop (Employee ↔ Payroll)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({
            "attendances", "documents", "user",
            "reportingManager", "hrbp", "createdAt", "updatedAt"
    })
    private Employee employee;

    // ✅ Prevent loop & unnecessary fields
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "structure_id", nullable = false)
    @JsonIgnoreProperties({
            "createdAt", "updatedAt", "createdBy", "updatedBy", "isActive"
    })
    private SalaryStructureMaster salaryStructure;

    private String month;

    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private String bankReferenceNo;

    private Boolean isActive = true;

    private String createdBy;
    private String updatedBy;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ✅ Automatically set timestamps before saving
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ Enum for payment status
    public enum PaymentStatus {
        PENDING,
        PROCESSED,
        FAILED
    }
}
