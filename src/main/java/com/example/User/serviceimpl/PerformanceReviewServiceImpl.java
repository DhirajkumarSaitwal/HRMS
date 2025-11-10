package com.example.User.serviceimpl;

import com.example.User.dto.PerformanceReviewDto;
import com.example.User.entity.KpiMaster;
import com.example.User.entity.PerformanceReview;
import com.example.User.entity.ReviewStatus;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.repository.PerformanceReviewRepository;
import com.example.User.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;
    @Autowired
    private KpiMasterRepository kpiMasterRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public PerformanceReviewDto createReview(PerformanceReviewDto dto) {
        KpiMaster kpi = kpiMasterRepository.findById(dto.getKpiId())
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found with ID: " + dto.getKpiId()));
        employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
        BigDecimal finalScore = null;
        if (dto.getSelfScore() != null && dto.getManagerScore() != null) {
            finalScore = dto.getSelfScore().add(dto.getManagerScore())
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        }
        PerformanceReview review = PerformanceReview.builder()
                .employeeId(dto.getEmployeeId())
                .kpi(kpi)
                .reviewPeriod(dto.getReviewPeriod())
                .selfScore(dto.getSelfScore() != null ? dto.getSelfScore() : BigDecimal.ZERO)
                .managerScore(dto.getManagerScore() != null ? dto.getManagerScore() : BigDecimal.ZERO)
                .finalScore(finalScore)
                .feedback(dto.getFeedback())
                .reviewStatus(dto.getReviewStatus() != null ? dto.getReviewStatus() : ReviewStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(dto.getIsActive())
                .build();
        PerformanceReview saved = performanceReviewRepository.save(review);
        return mapToDto(saved);
    }

    @Override
    public List<PerformanceReviewDto> getAllReviews() {
        return performanceReviewRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PerformanceReviewDto getReviewById(Long reviewId) {
        PerformanceReview review = performanceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Performance Review not found with ID: " + reviewId));

        return mapToDto(review);
    }

    @Override
    public PerformanceReviewDto updateReview(Long reviewId, PerformanceReviewDto dto) {
        PerformanceReview review = performanceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Performance Review not found with ID: " + reviewId));
        if (dto.getSelfScore() != null) review.setSelfScore(dto.getSelfScore());
        if (dto.getManagerScore() != null) review.setManagerScore(dto.getManagerScore());
        if (dto.getFeedback() != null) review.setFeedback(dto.getFeedback());
        if (dto.getReviewStatus() != null) review.setReviewStatus(dto.getReviewStatus());
        if (dto.getReviewPeriod() != null) review.setReviewPeriod(dto.getReviewPeriod());
        if (review.getSelfScore() != null && review.getManagerScore() != null) {
            review.setFinalScore(review.getSelfScore()
                    .add(review.getManagerScore())
                    .divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP));
        }
        review.setUpdatedAt(LocalDateTime.now());
        PerformanceReview updated = performanceReviewRepository.save(review);
        return mapToDto(updated);
    }

    @Override
    public String softDeleteReview(Long reviewId) {
        PerformanceReview review = performanceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Performance Review not found with ID: " + reviewId));
        // Mark as inactive (soft delete)
        review.setIsActive(false);
        review.setUpdatedAt(LocalDateTime.now());
        performanceReviewRepository.save(review);

        return "Performance Review with ID " + reviewId + " soft deleted successfully.";
    }

    @Override
    public String hardDeleteReview(Long reviewId) {
        PerformanceReview review = performanceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Performance Review not found with ID: " + reviewId));
        performanceReviewRepository.delete(review);  // Permanent delete
        return "Performance Review with ID " + reviewId + " permanently deleted successfully.";
    }



















    // ✅ Mapper method
    private PerformanceReviewDto mapToDto(PerformanceReview review) {
        return PerformanceReviewDto.builder()
                .reviewId(review.getReviewId())
                .employeeId(review.getEmployeeId())
                .kpiId(review.getKpi().getKpiId())
                .reviewPeriod(review.getReviewPeriod())
                .selfScore(review.getSelfScore())
                .managerScore(review.getManagerScore())
                .finalScore(review.getFinalScore())
                .feedback(review.getFeedback())
                .reviewStatus(review.getReviewStatus())
                .isActive(review.getIsActive())
                .build();
    }
}
