package com.example.User.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PerformanceReviewDTO {
    private Long employeeId;
    private Long kpiId;
    private String reviewPeriod;
    private BigDecimal selfScore;
    private BigDecimal managerScore;
    private String feedback;
    private String reviewStatus;
}
