package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;   // FK to employee table

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_id", nullable = false)
    private KpiMaster kpi;     // FK to kpi_master table

    @Column(name = "review_period", length = 50, nullable = false)
    private String reviewPeriod;  // e.g. Q1-2025

    @Column(name = "self_score", precision = 5, scale = 2)
    private BigDecimal selfScore;

    @Column(name = "manager_score", precision = 5, scale = 2)
    private BigDecimal managerScore;

    @Column(name = "final_score", precision = 5, scale = 2)
    private BigDecimal finalScore;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", length = 20, nullable = false)
    private ReviewStatus reviewStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;


    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.reviewStatus == null) this.reviewStatus = ReviewStatus.PENDING;
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
