package com.example.User.repository;

import com.example.User.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByIsActiveTrue();

}
