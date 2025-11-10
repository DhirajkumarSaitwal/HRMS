package com.example.User.service;

import com.example.User.dto.PerformanceDashboardSummaryDto;

import java.util.List;

public interface PerformanceDashboardSummaryService {

    // Create / Generate dashboard summary
    PerformanceDashboardSummaryDto createDashboard(PerformanceDashboardSummaryDto dto);

    // Get all dashboard summaries
    List<PerformanceDashboardSummaryDto> getAllDashboards();

    // Get dashboard by ID
    PerformanceDashboardSummaryDto getDashboardById(Long dashboardId);

    // Update dashboard
    PerformanceDashboardSummaryDto updateDashboard(Long dashboardId, PerformanceDashboardSummaryDto dto);

    // Delete dashboard
    void deleteDashboard(Long dashboardId);
}


