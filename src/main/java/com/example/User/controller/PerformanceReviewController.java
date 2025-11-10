package com.example.User.controller;

import com.example.User.dto.PerformanceReviewDto;
import com.example.User.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/review")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService performanceReviewService;

    // Create Performance Review
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('MANAGER') or hasRole('EMPLOYEE')")
    public ResponseEntity<PerformanceReviewDto> createReview(@RequestBody PerformanceReviewDto dto) {
        PerformanceReviewDto created = performanceReviewService.createReview(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get All Reviews
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<PerformanceReviewDto>> getAllReviews() {
        List<PerformanceReviewDto> allReviews = performanceReviewService.getAllReviews();
        return new ResponseEntity<>(allReviews, HttpStatus.OK);
    }

    // Get Review by ID
    @GetMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<PerformanceReviewDto> getReviewById(@PathVariable Long reviewId) {
        PerformanceReviewDto review = performanceReviewService.getReviewById(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // Update Performance Review
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<PerformanceReviewDto> updateReview(
            @PathVariable Long reviewId,
            @RequestBody PerformanceReviewDto dto) {

        PerformanceReviewDto updatedReview = performanceReviewService.updateReview(reviewId, dto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Soft Delete Review
    @DeleteMapping("/soft/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<String> softDeleteReview(@PathVariable Long reviewId) {
        String response = performanceReviewService.softDeleteReview(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Hard Delete Performance Review
    @DeleteMapping("/hard/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<String> hardDeleteReview(@PathVariable Long reviewId) {
        String response = performanceReviewService.hardDeleteReview(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
