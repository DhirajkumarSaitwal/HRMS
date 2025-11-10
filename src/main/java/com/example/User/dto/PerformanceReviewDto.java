package com.example.User.dto;

import com.example.User.entity.ReviewStatus;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewDto {

    private Long reviewId;
    private Long employeeId;
    private Long kpiId;          // FK reference to KPI Master
    private String reviewPeriod;
    private BigDecimal selfScore;
    private BigDecimal managerScore;
    private BigDecimal finalScore;
    private String feedback;
    private ReviewStatus reviewStatus;
    private Boolean isActive = true;
}
