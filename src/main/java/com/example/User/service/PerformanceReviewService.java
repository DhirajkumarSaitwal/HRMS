package com.example.User.service;

import com.example.User.dto.PerformanceReviewDTO;
import com.example.User.entity.PerformanceReview;
import java.util.List;

public interface PerformanceReviewService {
    PerformanceReview createReview(PerformanceReviewDTO reviewDTO);
    PerformanceReview updateReview(Long id, PerformanceReviewDTO reviewDTO);
    void deleteReview(Long id);
    List<PerformanceReview> getReviewsByEmployee(Long employeeId);
    PerformanceReview getReviewById(Long id);
    List<PerformanceReview> getAllReviews();

    void hardDeleteReview(Long id);

    void softDeleteReview(Long id);
}
