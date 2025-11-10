package com.example.User.service;

import com.example.User.dto.PerformanceReviewDto;
import java.util.List;

public interface PerformanceReviewService {
    PerformanceReviewDto createReview(PerformanceReviewDto dto);

    List<PerformanceReviewDto> getAllReviews();
    PerformanceReviewDto getReviewById(Long reviewId);
    PerformanceReviewDto updateReview(Long reviewId, PerformanceReviewDto dto);
    String softDeleteReview(Long reviewId);
    String hardDeleteReview(Long reviewId);




}
