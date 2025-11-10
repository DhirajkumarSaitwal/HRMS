package com.example.User.serviceimpl;

import com.example.User.dto.PerformanceDashboardSummaryDto;
import com.example.User.entity.PerformanceDashboardSummary;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.PerformanceDashboardSummaryRepository;
import com.example.User.service.PerformanceDashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceDashboardSummaryServiceImpl implements PerformanceDashboardSummaryService {

    private final PerformanceDashboardSummaryRepository repository;

    // Create / Generate dashboard
    @Override
    public PerformanceDashboardSummaryDto createDashboard(PerformanceDashboardSummaryDto dto) {
        PerformanceDashboardSummary dashboard = PerformanceDashboardSummary.builder()
                .departmentName(dto.getDepartmentName())
                .avgScore(dto.getAvgScore() != null ? dto.getAvgScore() : BigDecimal.ZERO)
                .totalEmployees(dto.getTotalEmployees())
                .topScore(dto.getTopScore() != null ? dto.getTopScore() : BigDecimal.ZERO)
                .bottomScore(dto.getBottomScore() != null ? dto.getBottomScore() : BigDecimal.ZERO)
                .reviewPeriod(dto.getReviewPeriod())
                .generatedAt(LocalDateTime.now())
                .build();

        PerformanceDashboardSummary saved = repository.save(dashboard);
        return mapToDto(saved);
    }

    // Get all dashboards
    @Override
    public List<PerformanceDashboardSummaryDto> getAllDashboards() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get dashboard by ID
    @Override
    public PerformanceDashboardSummaryDto getDashboardById(Long dashboardId) {
        PerformanceDashboardSummary dashboard = repository.findById(dashboardId)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found with id: " + dashboardId));
        return mapToDto(dashboard);
    }

    // Update dashboard
    @Override
    public PerformanceDashboardSummaryDto updateDashboard(Long dashboardId, PerformanceDashboardSummaryDto dto) {
        PerformanceDashboardSummary existing = repository.findById(dashboardId)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found with id: " + dashboardId));

        existing.setDepartmentName(dto.getDepartmentName());
        existing.setAvgScore(dto.getAvgScore() != null ? dto.getAvgScore() : BigDecimal.ZERO);
        existing.setTotalEmployees(dto.getTotalEmployees());
        existing.setTopScore(dto.getTopScore() != null ? dto.getTopScore() : BigDecimal.ZERO);
        existing.setBottomScore(dto.getBottomScore() != null ? dto.getBottomScore() : BigDecimal.ZERO);
        existing.setReviewPeriod(dto.getReviewPeriod());
        existing.setGeneratedAt(LocalDateTime.now());

        PerformanceDashboardSummary updated = repository.save(existing);
        return mapToDto(updated);
    }

    // Delete dashboard
    @Override
    public void deleteDashboard(Long dashboardId) {
        PerformanceDashboardSummary existing = repository.findById(dashboardId)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found with id: " + dashboardId));
        repository.delete(existing);
    }

    // Mapping Entity to DTO
    private PerformanceDashboardSummaryDto mapToDto(PerformanceDashboardSummary dashboard) {
        return PerformanceDashboardSummaryDto.builder()
                .dashboardId(dashboard.getDashboardId())
                .departmentName(dashboard.getDepartmentName())
                .avgScore(dashboard.getAvgScore())
                .totalEmployees(dashboard.getTotalEmployees())
                .topScore(dashboard.getTopScore())
                .bottomScore(dashboard.getBottomScore())
                .reviewPeriod(dashboard.getReviewPeriod())
                .generatedAt(dashboard.getGeneratedAt())
                .build();
    }
}
