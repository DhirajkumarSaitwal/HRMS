package com.example.User.controller;

import com.example.User.dto.PerformanceDashboardSummaryDto;
import com.example.User.service.PerformanceDashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/dashboard")
@RequiredArgsConstructor
public class PerformanceDashboardSummaryController {

    private final PerformanceDashboardSummaryService service;

    // Create Dashboard
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceDashboardSummaryDto> createDashboard(@RequestBody PerformanceDashboardSummaryDto dto) {
        PerformanceDashboardSummaryDto created = service.createDashboard(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get All Dashboards
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<PerformanceDashboardSummaryDto>> getAllDashboards() {
        List<PerformanceDashboardSummaryDto> list = service.getAllDashboards();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get Dashboard by ID
    @GetMapping("/{dashboardId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<PerformanceDashboardSummaryDto> getDashboardById(@PathVariable Long dashboardId) {
        PerformanceDashboardSummaryDto dto = service.getDashboardById(dashboardId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Update Dashboard
    @PutMapping("/{dashboardId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<PerformanceDashboardSummaryDto> updateDashboard(
            @PathVariable Long dashboardId,
            @RequestBody PerformanceDashboardSummaryDto dto) {
        PerformanceDashboardSummaryDto updated = service.updateDashboard(dashboardId, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete Dashboard
    @DeleteMapping("/{dashboardId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<String> deleteDashboard(@PathVariable Long dashboardId) {
        service.deleteDashboard(dashboardId);
        return new ResponseEntity<>("Dashboard deleted successfully", HttpStatus.OK);
    }
}

