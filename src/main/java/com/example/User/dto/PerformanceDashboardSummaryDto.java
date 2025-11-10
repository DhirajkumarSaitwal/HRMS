package com.example.User.dto;



import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDashboardSummaryDto {

    private Long dashboardId;

    private String departmentName;

    private BigDecimal avgScore;

    private Integer totalEmployees;

    private BigDecimal topScore;

    private BigDecimal bottomScore;

    private String reviewPeriod;

    private LocalDateTime generatedAt;
}

