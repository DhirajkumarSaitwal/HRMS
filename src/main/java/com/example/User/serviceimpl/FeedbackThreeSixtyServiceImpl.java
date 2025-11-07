package com.example.User.serviceimpl;
import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.FeedbackThreeSixty;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.FeedbackThreeSixtyRepository;
import com.example.User.service.FeedbackThreeSixtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FeedbackThreeSixtyServiceImpl implements FeedbackThreeSixtyService {

    @Autowired
    private FeedbackThreeSixtyRepository feedbackThreeSixtyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public FeedbackThreeSixty createFeedback(FeedbackThreeSixtyDTO dto) {
        // Validate reviewer and reviewee exist
        Employee reviewer = employeeRepository.findById(dto.getReviewerId()).orElse(null);
        if (reviewer == null) {
            throw new RuntimeException("Reviewer (employee) not found!");
        }
        Employee reviewee = employeeRepository.findById(dto.getRevieweeId()).orElse(null);
        if (reviewee == null) {
            throw new RuntimeException("Reviewee (employee) not found!");
        }
        // prevent self feedback
        if (dto.getReviewerId().equals(dto.getRevieweeId())) {
            throw new RuntimeException("Reviewer and reviewee cannot be same!");
        }

        // rating validation (example: 0 - 10)
        if (dto.getRating() != null) {
            BigDecimal r = dto.getRating();
            if (r.compareTo(BigDecimal.ZERO) < 0 || r.compareTo(new BigDecimal(10)) > 0) {
                throw new RuntimeException("Rating must be between 0 and 10");
            }
        }

        FeedbackThreeSixty feedback = new FeedbackThreeSixty();
        feedback.setReviewer(reviewer);
        feedback.setReviewee(reviewee);
        feedback.setComments(dto.getComments());
        feedback.setRating(dto.getRating());
        feedback.setIsActive(true);
        feedback.setCreatedBy("ADMIN");
        feedback.setUpdatedBy("ADMIN");

        return feedbackThreeSixtyRepository.save(feedback);
    }

    @Override
    public FeedbackThreeSixty updateFeedback(Long id, FeedbackThreeSixtyDTO dto) {
        FeedbackThreeSixty existing = feedbackThreeSixtyRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Feedback not found!");
        }

        // allow update of comments and rating only (keep reviewer/reviewee same)
        existing.setComments(dto.getComments());
        existing.setRating(dto.getRating());
        existing.setUpdatedBy("ADMIN");

        return feedbackThreeSixtyRepository.save(existing);
    }

    @Override
    public void softDeleteFeedback(Long id) {
        FeedbackThreeSixty existing = feedbackThreeSixtyRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Feedback not found!");
        }
        existing.setIsActive(false);
        existing.setUpdatedBy("HR/ADMIN - Soft Delete");
        feedbackThreeSixtyRepository.save(existing);
    }

    @Override
    public void hardDeleteFeedback(Long id) {
        FeedbackThreeSixty existing = feedbackThreeSixtyRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Feedback not found!");
        }
        feedbackThreeSixtyRepository.delete(existing);
    }

    @Override
    public FeedbackThreeSixty getFeedbackById(Long id) {
        FeedbackThreeSixty f = feedbackThreeSixtyRepository.findById(id).orElse(null);
        if (f == null) {
            throw new RuntimeException("Feedback not found!");
        }
        return f;
    }

    @Override
    public List<FeedbackThreeSixty> getFeedbacksByReviewee(Long revieweeId) {
        return feedbackThreeSixtyRepository.findByReviewee_EmployeeId(revieweeId);
    }

    @Override
    public List<FeedbackThreeSixty> getAllActiveFeedbacks() {
        return feedbackThreeSixtyRepository.findByIsActiveTrue();
    }
}
