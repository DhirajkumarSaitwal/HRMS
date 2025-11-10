package com.example.User.service;

import com.example.User.dto.FeedbackThreeSixtyDto;

import java.util.List;

public interface FeedbackThreeSixtyService {

    FeedbackThreeSixtyDto addFeedback(FeedbackThreeSixtyDto dto);

    List<FeedbackThreeSixtyDto> getFeedbackByReviewee(Long revieweeId);

    List<FeedbackThreeSixtyDto> getFeedbackByReviewer(Long reviewerId);
    List<FeedbackThreeSixtyDto> getAllFeedbacks();
    FeedbackThreeSixtyDto getFeedbackById(Long feedbackId);
    FeedbackThreeSixtyDto updateFeedback(Long feedbackId, FeedbackThreeSixtyDto dto);
    void deleteFeedback(Long feedbackId);
}

