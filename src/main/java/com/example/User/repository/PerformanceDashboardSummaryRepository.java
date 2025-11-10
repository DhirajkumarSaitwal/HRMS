package com.example.User.repository;


import com.example.User.entity.PerformanceDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceDashboardSummaryRepository extends JpaRepository<PerformanceDashboardSummary, Long> {

}

