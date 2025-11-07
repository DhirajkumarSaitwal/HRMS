package com.example.User.service;
import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.entity.PerformanceDashboardSummary;
import java.util.List;

public interface PerformanceDashboardSummaryService {

    PerformanceDashboardSummary createDashboard(PerformanceDashboardSummaryDTO dto);
    PerformanceDashboardSummary updateDashboard(Long id, PerformanceDashboardSummaryDTO dto);
    List<PerformanceDashboardSummary> getAllDashboards(Boolean activeOnly);
    PerformanceDashboardSummary getDashboardById(Long id);
    void softDeleteDashboard(Long id);
    void hardDeleteDashboard(Long id);
}
