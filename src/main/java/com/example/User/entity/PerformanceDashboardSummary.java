package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance_dashboard_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDashboardSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashboardId;  // Primary key

    @Column(name = "department_name", length = 100)
    private String departmentName;

    @Column(name = "avg_score", precision = 5, scale = 2)
    private BigDecimal avgScore;

    @Column(name = "total_employees")
    private Integer totalEmployees;

    @Column(name = "top_score", precision = 5, scale = 2)
    private BigDecimal topScore;

    @Column(name = "bottom_score", precision = 5, scale = 2)
    private BigDecimal bottomScore;

    @Column(name = "review_period", length = 50)
    private String reviewPeriod;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}
