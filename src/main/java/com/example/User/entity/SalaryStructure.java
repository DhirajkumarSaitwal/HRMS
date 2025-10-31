package com.example.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(
        name = "salary_structure_master",
        uniqueConstraints = @UniqueConstraint(columnNames = "structureName")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructure {

    @Id
    @GeneratedValue
    private Long structureId;

    @NotBlank(message = "Structure name cannot be blank")
    @Column(nullable = false, length = 100)
    private String structureName;

    @DecimalMin(value = "0.0", message = "Basic pay cannot be negative")
    private double basicPay;

    @DecimalMin(value = "0.0", message = "HRA cannot be negative")
    private double hra;

    @DecimalMin(value = "0.0", message = "Variable pay cannot be negative")
    private double variablePay;

    @DecimalMin(value = "0.0", message = "Other allowances cannot be negative")
    private double otherAllowances;

    @DecimalMin(value = "0.0", message = "Deductions cannot be negative")
    private double deductions;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.createdBy == null) {
            this.createdBy = "SYSTEM";
        }
        if (this.updatedBy == null) {
            this.updatedBy = "SYSTEM";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (this.updatedBy == null) {
            this.updatedBy = "SYSTEM";
        }
    }
}
