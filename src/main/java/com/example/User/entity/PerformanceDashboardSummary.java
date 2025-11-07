package com.example.User.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "performance_dashboard_summary",
        uniqueConstraints = @UniqueConstraint(columnNames = {"department_name", "review_period"})
)
public class PerformanceDashboardSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashboardId;

    @NotNull
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String departmentName;

    @Min(0)
    @Max(100)
    @Column
    private Double avgScore;

    @Min(0)
    @Column
    private Integer totalEmployees;

    @Min(0)
    @Max(100)
    @Column
    private Double topScore;

    @Min(0)
    @Max(100)
    @Column
    private Double bottomScore;

    @NotNull
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String reviewPeriod;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.isActive == null) this.isActive = true;
        if (this.createdBy == null) this.createdBy = "ADMIN";
        if (this.updatedBy == null) this.updatedBy = "ADMIN";
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = "ADMIN";
    }
}
