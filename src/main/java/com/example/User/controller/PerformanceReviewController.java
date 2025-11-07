package com.example.User.controller;
import com.example.User.dto.PerformanceReviewDTO;
import com.example.User.entity.PerformanceReview;
import com.example.User.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/review")
@CrossOrigin("*")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService performanceReviewService;

    //  CREATE - Submit Performance Review
    // Roles: EMPLOYEE / MANAGER
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @PostMapping("/create")
    public PerformanceReview createReview(@RequestBody PerformanceReviewDTO reviewDTO) {
        return performanceReviewService.createReview(reviewDTO);
    }

    //  UPDATE - Update performance review (Manager/HR/Admin)
    @PreAuthorize("hasAnyRole('MANAGER', 'HR', 'ADMIN')")
    @PutMapping("/{id}")
    public PerformanceReview updateReview(@PathVariable Long id, @RequestBody PerformanceReviewDTO reviewDTO) {
        return performanceReviewService.updateReview(id, reviewDTO);
    }

    //  GET BY EMPLOYEE - Get review details
    // Roles: HR / ADMIN / MANAGER / EMPLOYEE
    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public List<PerformanceReview> getReviewsByEmployee(@PathVariable Long employeeId) {
        return performanceReviewService.getReviewsByEmployee(employeeId);
    }

    // GET ALL - Only HR and Admin can view all reviews
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @GetMapping("/all")
    public List<PerformanceReview> getAllReviews() {
        return performanceReviewService.getAllReviews();
    }

    // SOFT DELETE - HR or Admin can mark review as inactive
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    @PutMapping("/soft-delete/{id}")
    public String softDeleteReview(@PathVariable Long id) {
        performanceReviewService.softDeleteReview(id);
        return "Performance Review soft deleted (status set to INACTIVE) successfully!";
    }

    // HARD DELETE - Only Admin can permanently delete
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/hard-delete/{id}")
    public String hardDeleteReview(@PathVariable Long id) {
        performanceReviewService.hardDeleteReview(id);
        return "Performance Review permanently deleted successfully!";
    }

    //  GET BY REVIEW ID (optional detailed view)
    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/get/{id}")
    public PerformanceReview getReviewById(@PathVariable Long id) {
        return performanceReviewService.getReviewById(id);
    }
}
