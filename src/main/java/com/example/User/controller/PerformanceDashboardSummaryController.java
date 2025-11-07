package com.example.User.controller;
import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.entity.PerformanceDashboardSummary;
import com.example.User.service.PerformanceDashboardSummaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/dashboard")
@CrossOrigin("*")
public class PerformanceDashboardSummaryController {

    @Autowired
    private PerformanceDashboardSummaryService performanceDashboardSummaryService;

    // CREATE
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public PerformanceDashboardSummary createDashboard(@Valid @RequestBody PerformanceDashboardSummaryDTO dto) {
        return performanceDashboardSummaryService.createDashboard(dto);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public PerformanceDashboardSummary updateDashboard(@PathVariable Long id,
                                                       @Valid @RequestBody PerformanceDashboardSummaryDTO dto) {
        return performanceDashboardSummaryService.updateDashboard(id, dto);
    }

    // GET ALL
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public List<PerformanceDashboardSummary> getAllDashboards(@RequestParam(required = false) Boolean activeOnly) {
        return performanceDashboardSummaryService.getAllDashboards(activeOnly);
    }

    // GET BY ID
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    public PerformanceDashboardSummary getDashboardById(@PathVariable Long id) {
        return performanceDashboardSummaryService.getDashboardById(id);
    }

    // SOFT DELETE
    @PutMapping("/soft-delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public String softDeleteDashboard(@PathVariable Long id) {
        performanceDashboardSummaryService.softDeleteDashboard(id);
        return "Dashboard soft-deleted successfully (isActive=false)!";
    }

    // HARD DELETE
    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public String hardDeleteDashboard(@PathVariable Long id) {
        performanceDashboardSummaryService.hardDeleteDashboard(id);
        return "Dashboard permanently deleted!";
    }
}
