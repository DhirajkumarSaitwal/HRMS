package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary_structure_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"structure_name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "structure_id", updatable = false, nullable = false)
    private Long structureId;

    @Column(name = "structure_name", nullable = false, length = 100, unique = true)
    private String structureName;

    @Column(name = "basic_pay", precision = 12, scale = 2, nullable = false)
    private BigDecimal basicPay;

    @Column(name = "hra", precision = 12, scale = 2, nullable = false)
    private BigDecimal hra;

    @Column(name = "variable_pay", precision = 12, scale = 2)
    private BigDecimal variablePay;

    @Column(name = "other_allowances", precision = 12, scale = 2)
    private BigDecimal otherAllowances;

    @Column(name = "deductions", precision = 12, scale = 2)
    private BigDecimal deductions;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.deductions == null) this.deductions = BigDecimal.ZERO;
        if (this.variablePay == null) this.variablePay = BigDecimal.ZERO;
        if (this.otherAllowances == null) this.otherAllowances = BigDecimal.ZERO;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

