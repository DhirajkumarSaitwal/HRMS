package com.example.User.serviceimpl;
import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.entity.PerformanceDashboardSummary;
import com.example.User.repository.PerformanceDashboardSummaryRepository;
import com.example.User.service.PerformanceDashboardSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceDashboardSummaryServiceImpl implements PerformanceDashboardSummaryService {

    @Autowired
    private PerformanceDashboardSummaryRepository performanceDashboardSummaryRepository;

    @Override
    public PerformanceDashboardSummary createDashboard(PerformanceDashboardSummaryDTO dto) {
        if (performanceDashboardSummaryRepository.existsByDepartmentNameAndReviewPeriod(dto.getDepartmentName(), dto.getReviewPeriod())) {
            throw new RuntimeException("Dashboard for this department and review period already exists!");
        }
        PerformanceDashboardSummary dashboard = new PerformanceDashboardSummary();
        dashboard.setDepartmentName(dto.getDepartmentName());
        dashboard.setAvgScore(dto.getAvgScore());
        dashboard.setTotalEmployees(dto.getTotalEmployees());
        dashboard.setTopScore(dto.getTopScore());
        dashboard.setBottomScore(dto.getBottomScore());
        dashboard.setReviewPeriod(dto.getReviewPeriod());
        dashboard.setIsActive(true);
        dashboard.setCreatedBy("ADMIN");
        dashboard.setUpdatedBy("ADMIN");
        return performanceDashboardSummaryRepository.save(dashboard);
    }

    @Override
    public PerformanceDashboardSummary updateDashboard(Long id, PerformanceDashboardSummaryDTO dto) {
        PerformanceDashboardSummary dashboard = getDashboardById(id);
        dashboard.setDepartmentName(dto.getDepartmentName());
        dashboard.setAvgScore(dto.getAvgScore());
        dashboard.setTotalEmployees(dto.getTotalEmployees());
        dashboard.setTopScore(dto.getTopScore());
        dashboard.setBottomScore(dto.getBottomScore());
        dashboard.setReviewPeriod(dto.getReviewPeriod());
        dashboard.setUpdatedBy("ADMIN");
        return performanceDashboardSummaryRepository.save(dashboard);
    }

    @Override
    public List<PerformanceDashboardSummary> getAllDashboards(Boolean activeOnly) {
        if (activeOnly != null && activeOnly) return performanceDashboardSummaryRepository.findAllActive();
        return performanceDashboardSummaryRepository.findAll();
    }

    @Override
    public PerformanceDashboardSummary getDashboardById(Long id) {
        PerformanceDashboardSummary dashboard = performanceDashboardSummaryRepository.findById(id).orElse(null);
        if (dashboard == null) throw new RuntimeException("Dashboard not found with id: " + id);
        return dashboard;
    }

    @Override
    public void softDeleteDashboard(Long id) {
        PerformanceDashboardSummary dashboard = getDashboardById(id);
        dashboard.setIsActive(false);
        dashboard.setUpdatedBy("ADMIN");
        performanceDashboardSummaryRepository.save(dashboard);
    }

    @Override
    public void hardDeleteDashboard(Long id) {
        performanceDashboardSummaryRepository.deleteById(id);
    }
}
