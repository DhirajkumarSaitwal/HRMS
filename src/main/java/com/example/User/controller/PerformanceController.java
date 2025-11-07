package com.example.User.controller;

import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.dto.KpiMasterDto;
import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.dto.PerformanceReviewDto;
import com.example.User.service.FeedbackThreeSixtyService;
import com.example.User.service.KpiService;
import com.example.User.service.PerformanceDashboardSummaryService;
import com.example.User.service.PerformanceReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {
    @Autowired
    private  KpiService kpiService;
    @Autowired
    private  PerformanceReviewService reviewService;
    @Autowired
    private FeedbackThreeSixtyService service;
    @Autowired
    private PerformanceDashboardSummaryService dashboardSummary;

    @PostMapping("/create")
    public ResponseEntity<KpiMasterDto> createKpi(@RequestBody KpiMasterDto dto) {
        return ResponseEntity.ok(kpiService.createKpi(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<KpiMasterDto> updateKpi(@PathVariable Long id, @RequestBody KpiMasterDto dto) {
        return ResponseEntity.ok(kpiService.updateKpi(id, dto));
    }
    //------------Porfermonce Review -----------
    @PostMapping("/createReview")
    public ResponseEntity<PerformanceReviewDto> createReview(@RequestBody PerformanceReviewDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }
    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity<PerformanceReviewDto> updateReview(@PathVariable Long reviewId,
                                                             @RequestBody PerformanceReviewDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, dto));
    }
    @GetMapping("/getReview/{reviewId}")
    public ResponseEntity<PerformanceReviewDto> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceReviewDto>> getEmployeeReviews(@PathVariable Long employeeId) {
        return ResponseEntity.ok(reviewService.getReviewsByEmployee(employeeId));
    }
    @DeleteMapping("/Review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    //-----------------------feedback Api-----------------------------------

    @PostMapping("/feedback/create")
    public ResponseEntity<FeedbackThreeSixtyDTO> createFeedback(@Valid @RequestBody FeedbackThreeSixtyDTO dto) {
        return ResponseEntity.ok(service.createFeedback(dto));
    }
    @PutMapping("/feedback/update/{id}")
    public ResponseEntity<FeedbackThreeSixtyDTO> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackThreeSixtyDTO dto) {
        return ResponseEntity.ok(service.updateFeedback(id, dto));
    }
    @GetMapping("/getAllFeedback")
    public ResponseEntity<List<FeedbackThreeSixtyDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(service.getAllFeedbacks());
    }
    @GetMapping("/getFeedback/{id}")
    public ResponseEntity<FeedbackThreeSixtyDTO> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFeedbackById(id));
    }
    @GetMapping("/Feedbackreviewee/{revieweeId}")
    public ResponseEntity<List<FeedbackThreeSixtyDTO>> getFeedbacksByReviewee(@PathVariable Long revieweeId) {
        return ResponseEntity.ok(service.getFeedbacksByReviewee(revieweeId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        service.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully");
    }

    //----------------------------  Performance Dashboard---------------------

    @PostMapping("/dashboard")
    public ResponseEntity<PerformanceDashboardSummaryDTO> createDashboard(@RequestBody PerformanceDashboardSummaryDTO dto) {
        return ResponseEntity.ok(dashboardSummary.createDashboardSummary(dto));
    }
    @GetMapping("/dashboard/{id}")
    public ResponseEntity<PerformanceDashboardSummaryDTO> getDashboardById(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardSummary.getDashboardById(id));
    }
    @GetMapping("/gellAllDashboard")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getAllDashboards() {
        return ResponseEntity.ok(dashboardSummary.getAllDashboards());
    }
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getByDepartment(@PathVariable String departmentName) {
        return ResponseEntity.ok(dashboardSummary.getDashboardByDepartment(departmentName));
    }
    @GetMapping("/period/{reviewPeriod}")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getByPeriod(@PathVariable String reviewPeriod) {
        return ResponseEntity.ok(dashboardSummary.getDashboardByReviewPeriod(reviewPeriod));
    }
    @DeleteMapping("/dashboard/{id}")
    public ResponseEntity<String> deleteDashboard(@PathVariable Long id) {
        dashboardSummary.deleteDashboard(id);
        return ResponseEntity.ok("Dashboard deleted successfully with ID: " + id);
    }
}
