package com.example.User.repository;

import com.example.User.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    PerformanceReview findByEmployee_EmployeeIdAndReviewPeriod(Long employeeId, String reviewPeriod);
    List<PerformanceReview> findByEmployee_EmployeeId(Long employeeId);
}
