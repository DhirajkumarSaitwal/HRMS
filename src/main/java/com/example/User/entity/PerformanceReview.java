package com.example.User.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "performance_review",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "review_period"}))
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "attendances",
            "documents",
            "user",
            "reportingManager",
            "hrbp"
    })
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private KpiMaster kpi;

    @Column(nullable = false, length = 50)
    private String reviewPeriod;

    private BigDecimal selfScore;
    private BigDecimal managerScore;
    private BigDecimal finalScore;

    @Column(length = 1000)
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus;

    // 🔹 Active/Inactive flag
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ---- Lifecycle Hooks ----
    @PrePersist
    public void prePersist() {
        if (selfScore != null && managerScore != null) {
            this.finalScore = selfScore.add(managerScore).divide(BigDecimal.valueOf(2));
        }
        if (this.reviewStatus == null)
            this.reviewStatus = ReviewStatus.PENDING;
        if (this.createdBy == null) this.createdBy = "ADMIN";
        if (this.updatedBy == null) this.updatedBy = "ADMIN";
        if (this.isActive == null) this.isActive = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = "ADMIN";
        if (selfScore != null && managerScore != null) {
            this.finalScore = selfScore.add(managerScore).divide(BigDecimal.valueOf(2));
        }
    }
}
