package com.example.User.repository;

import com.example.User.entity.PerformanceDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceDashboardSummaryRepository extends JpaRepository<PerformanceDashboardSummary, Long> {

    @Query("SELECT d FROM PerformanceDashboardSummary d WHERE d.isActive = true")
    List<PerformanceDashboardSummary> findAllActive();

    boolean existsByDepartmentNameAndReviewPeriod(String departmentName, String reviewPeriod);
}
