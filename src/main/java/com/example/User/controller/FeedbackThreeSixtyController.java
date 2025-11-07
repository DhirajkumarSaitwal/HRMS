package com.example.User.controller;
import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.entity.FeedbackThreeSixty;
import com.example.User.service.FeedbackThreeSixtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/feedback")
@CrossOrigin("*")
public class FeedbackThreeSixtyController {

    @Autowired
    private FeedbackThreeSixtyService feedbackThreeSixtyService;

    // Create feedback — peers / managers
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @PostMapping
    public FeedbackThreeSixty createFeedback(@RequestBody FeedbackThreeSixtyDTO dto) {
        return feedbackThreeSixtyService.createFeedback(dto);
    }

    // Update feedback — reviewer or HR/Admin/Manager
    @PreAuthorize("hasAnyRole('MANAGER','HR','ADMIN')")
    @PutMapping("/{id}")
    public FeedbackThreeSixty updateFeedback(@PathVariable Long id, @RequestBody FeedbackThreeSixtyDTO dto) {
        return feedbackThreeSixtyService.updateFeedback(id, dto);
    }

    // Get all feedbacks for a reviewee (employee)
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/reviewee/{revieweeId}")
    public List<FeedbackThreeSixty> getByReviewee(@PathVariable Long revieweeId) {
        return feedbackThreeSixtyService.getFeedbacksByReviewee(revieweeId);
    }

    // Get a single feedback by id
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public FeedbackThreeSixty getById(@PathVariable Long id) {
        return feedbackThreeSixtyService.getFeedbackById(id);
    }

    // Get all active feedbacks (HR/Admin)
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @GetMapping("/all")
    public List<FeedbackThreeSixty> getAllActive() {
        return feedbackThreeSixtyService.getAllActiveFeedbacks();
    }

    // Soft delete (HR/Admin)
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PutMapping("/soft-delete/{id}")
    public String softDelete(@PathVariable Long id) {
        feedbackThreeSixtyService.softDeleteFeedback(id);
        return "Feedback soft deleted (isActive=false)";
    }

    // Hard delete (HR)
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/hard-delete/{id}")
    public String hardDelete(@PathVariable Long id) {
        feedbackThreeSixtyService.hardDeleteFeedback(id);
        return "Feedback permanently deleted";
    }
}
