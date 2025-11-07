package com.example.User.serviceimpl;
import com.example.User.dto.PerformanceReviewDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.KpiMaster;
import com.example.User.entity.PerformanceReview;
import com.example.User.entity.ReviewStatus;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.repository.PerformanceReviewRepository;
import com.example.User.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KpiMasterRepository kpiMasterRepository;

    @Override
    public PerformanceReview createReview(PerformanceReviewDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found!");
        }

        KpiMaster kpi = kpiMasterRepository.findById(dto.getKpiId()).orElse(null);
        if (kpi == null) {
            throw new RuntimeException("KPI not found!");
        }

        PerformanceReview existing = performanceReviewRepository
                .findByEmployee_EmployeeIdAndReviewPeriod(dto.getEmployeeId(), dto.getReviewPeriod());
        if (existing != null) {
            throw new RuntimeException("Review already exists for this employee and period!");
        }

        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setKpi(kpi);
        review.setReviewPeriod(dto.getReviewPeriod());
        review.setSelfScore(dto.getSelfScore());
        review.setManagerScore(dto.getManagerScore());
        review.setFeedback(dto.getFeedback());

        if (dto.getReviewStatus() != null) {
            review.setReviewStatus(ReviewStatus.valueOf(dto.getReviewStatus()));
        } else {
            review.setReviewStatus(ReviewStatus.PENDING);
        }

        review.setCreatedBy("ADMIN");
        review.setUpdatedBy("ADMIN");

        if (dto.getSelfScore() != null && dto.getManagerScore() != null) {
            review.setFinalScore(dto.getSelfScore().add(dto.getManagerScore())
                    .divide(new BigDecimal(2)));
        }

        return performanceReviewRepository.save(review);
    }

    @Override
    public PerformanceReview updateReview(Long id, PerformanceReviewDTO dto) {
        PerformanceReview existing = performanceReviewRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Review not found!");
        }

        existing.setSelfScore(dto.getSelfScore());
        existing.setManagerScore(dto.getManagerScore());
        existing.setFeedback(dto.getFeedback());

        if (dto.getReviewStatus() != null) {
            existing.setReviewStatus(ReviewStatus.valueOf(dto.getReviewStatus()));
        }

        if (dto.getSelfScore() != null && dto.getManagerScore() != null) {
            existing.setFinalScore(dto.getSelfScore().add(dto.getManagerScore())
                    .divide(new BigDecimal(2)));
        }

        existing.setUpdatedBy("ADMIN");

        return performanceReviewRepository.save(existing);
    }

    // ---------------- SOFT DELETE ----------------
    @Override
    public void softDeleteReview(Long id) {
        PerformanceReview review = performanceReviewRepository.findById(id).orElse(null);
        if (review == null) {
            throw new RuntimeException("Performance Review not found!");
        }

        review.setReviewStatus(ReviewStatus.INACTIVE);//  Corrected
        review.setIsActive(false);
        review.setUpdatedBy("HR/ADMIN - Soft Delete");
        performanceReviewRepository.save(review);
    }

    // ---------------- HARD DELETE ----------------
    @Override
    public void hardDeleteReview(Long id) {
        PerformanceReview review = performanceReviewRepository.findById(id).orElse(null);
        if (review == null) {
            throw new RuntimeException("Performance Review not found!");
        }
        performanceReviewRepository.delete(review);
    }

    @Override
    public List<PerformanceReview> getReviewsByEmployee(Long employeeId) {
        return performanceReviewRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public PerformanceReview getReviewById(Long id) {
        PerformanceReview review = performanceReviewRepository.findById(id).orElse(null);
        if (review == null) {
            throw new RuntimeException("Review not found!");
        }
        return review;
    }

    @Override
    public List<PerformanceReview> getAllReviews() {
        return performanceReviewRepository.findAll();
    }

    @Override
    public void deleteReview(Long id) {
        PerformanceReview existing = performanceReviewRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Review not found!");
        }
        performanceReviewRepository.delete(existing);
    }
}
