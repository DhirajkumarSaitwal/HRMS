package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_benefit_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeBenefitMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id", updatable = false, nullable = false)
    private Long benefitsMappingId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;  // FK to employee_master

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id")
    private BenefitMaster benefit;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private BenefitProvider provider;

    private BigDecimal coverageAmount;
    private BigDecimal premiumAmount;
    private BigDecimal employerContribution;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;
@CreationTimestamp
    private LocalDateTime createdAt;
@UpdateTimestamp
    private LocalDateTime updatedAt;



    public enum Status {
        Active, Inactive, Pending
    }
}

