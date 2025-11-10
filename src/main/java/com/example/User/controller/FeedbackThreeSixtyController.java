package com.example.User.controller;

import com.example.User.dto.FeedbackThreeSixtyDto;
import com.example.User.service.FeedbackThreeSixtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/feedback")
@RequiredArgsConstructor
public class FeedbackThreeSixtyController {

    private final FeedbackThreeSixtyService service;

    // Add feedback
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('MANAGER')")
    public ResponseEntity<FeedbackThreeSixtyDto> addFeedback(@RequestBody FeedbackThreeSixtyDto dto) {
        FeedbackThreeSixtyDto created = service.addFeedback(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get feedback received by an employee
    @GetMapping("/reviewee/{revieweeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<FeedbackThreeSixtyDto>> getFeedbackByReviewee(@PathVariable Long revieweeId) {
        List<FeedbackThreeSixtyDto> feedbacks = service.getFeedbackByReviewee(revieweeId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedback given by an employee
    @GetMapping("/reviewer/{reviewerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<FeedbackThreeSixtyDto>> getFeedbackByReviewer(@PathVariable Long reviewerId) {
        List<FeedbackThreeSixtyDto> feedbacks = service.getFeedbackByReviewer(reviewerId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // GET all feedbacks (Admin/HR)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<FeedbackThreeSixtyDto>> getAllFeedbacks() {
        List<FeedbackThreeSixtyDto> feedbacks = service.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // GET feedback by feedbackId
    @GetMapping("/{feedbackId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<FeedbackThreeSixtyDto> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackThreeSixtyDto feedback = service.getFeedbackById(feedbackId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    // PUT: Update feedback by feedbackId
    @PutMapping("/{feedbackId}")
    public ResponseEntity<FeedbackThreeSixtyDto> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody FeedbackThreeSixtyDto dto) {

        FeedbackThreeSixtyDto updatedFeedback = service.updateFeedback(feedbackId, dto);
        return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
    }

    // DELETE feedback by feedbackId
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
        service.deleteFeedback(feedbackId);
        return new ResponseEntity<>("Feedback deleted successfully", HttpStatus.OK);
    }
}

