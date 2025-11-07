package com.example.User.dto;
import jakarta.validation.constraints.*;

public class PerformanceDashboardSummaryDTO {

    @NotNull
    @Size(max = 100)
    private String departmentName;

    @Min(0)
    @Max(100)
    private Double avgScore;

    @Min(0)
    private Integer totalEmployees;

    @Min(0)
    @Max(100)
    private Double topScore;

    @Min(0)
    @Max(100)
    private Double bottomScore;

    @NotNull
    @Size(max = 50)
    private String reviewPeriod;

    // Getters and Setters
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public Double getAvgScore() { return avgScore; }
    public void setAvgScore(Double avgScore) { this.avgScore = avgScore; }

    public Integer getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(Integer totalEmployees) { this.totalEmployees = totalEmployees; }

    public Double getTopScore() { return topScore; }
    public void setTopScore(Double topScore) { this.topScore = topScore; }

    public Double getBottomScore() { return bottomScore; }
    public void setBottomScore(Double bottomScore) { this.bottomScore = bottomScore; }

    public String getReviewPeriod() { return reviewPeriod; }
    public void setReviewPeriod(String reviewPeriod) { this.reviewPeriod = reviewPeriod; }
}
