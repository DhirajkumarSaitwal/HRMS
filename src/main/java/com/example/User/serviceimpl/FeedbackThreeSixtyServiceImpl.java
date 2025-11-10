package com.example.User.serviceimpl;

import com.example.User.dto.FeedbackThreeSixtyDto;
import com.example.User.entity.Employee;
import com.example.User.entity.FeedbackThreeSixty;
import com.example.User.entity.User;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.FeedbackThreeSixtyRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.FeedbackThreeSixtyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackThreeSixtyServiceImpl implements FeedbackThreeSixtyService {
    @Autowired
    private final FeedbackThreeSixtyRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public FeedbackThreeSixtyDto addFeedback(FeedbackThreeSixtyDto dto) {
        User reviewer = userRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new EntityNotFoundException("Reviewer not found"));
        Employee reviewee = employeeRepository.findById(dto.getRevieweeId())
                .orElseThrow(() -> new EntityNotFoundException("Reviewee not found"));
        FeedbackThreeSixty feedback = FeedbackThreeSixty.builder()
                .reviewer(reviewer)
                .reviewee(reviewee)
                .comments(dto.getComments())
                .rating(dto.getRating())
                .createdAt(LocalDateTime.now())
                .build();
        FeedbackThreeSixty saved = repository.save(feedback);
        return mapToDto(saved);
    }

    @Override
    public List<FeedbackThreeSixtyDto> getFeedbackByReviewee(Long revieweeId) {
        List<FeedbackThreeSixty> list = repository.findByRevieweeId(revieweeId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No feedback found for revieweeId: " + revieweeId);
        }
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackThreeSixtyDto> getFeedbackByReviewer(Long reviewerId) {
        List<FeedbackThreeSixty> list = repository.findByReviewerId(reviewerId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No feedback found for reviewerId: " + reviewerId);
        }
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackThreeSixtyDto> getAllFeedbacks() {
        List<FeedbackThreeSixty> list = repository.findAll();
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public FeedbackThreeSixtyDto getFeedbackById(Long feedbackId) {
        FeedbackThreeSixty feedback = repository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));
        return mapToDto(feedback);
    }

    @Override
    public FeedbackThreeSixtyDto updateFeedback(Long feedbackId, FeedbackThreeSixtyDto dto) {
        FeedbackThreeSixty existing = repository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        existing.setComments(dto.getComments());
        existing.setRating(dto.getRating());

        FeedbackThreeSixty updated = repository.save(existing);
        return mapToDto(updated);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        FeedbackThreeSixty existing = repository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        repository.delete(existing);
    }


    // Utility method
    private FeedbackThreeSixtyDto mapToDto(FeedbackThreeSixty feedback) {
        return FeedbackThreeSixtyDto.builder()
                .feedbackId(feedback.getFeedbackId())
                .reviewerId(feedback.getReviewer().getId())
                .revieweeId(feedback.getReviewee().getEmployeeId())
                .comments(feedback.getComments())
                .rating(feedback.getRating())
                .createdAt(feedback.getCreatedAt())
                .build();
    }

}

