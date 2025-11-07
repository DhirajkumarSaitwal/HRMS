package com.example.User.repository;
import com.example.User.entity.FeedbackThreeSixty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackThreeSixtyRepository extends JpaRepository<FeedbackThreeSixty, Long> {
    List<FeedbackThreeSixty> findByReviewee_EmployeeId(Long revieweeId);
    List<FeedbackThreeSixty> findByReviewer_EmployeeId(Long reviewerId);
    List<FeedbackThreeSixty> findByIsActiveTrue();
}
