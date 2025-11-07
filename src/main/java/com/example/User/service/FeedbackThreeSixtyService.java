package com.example.User.service;
import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.entity.FeedbackThreeSixty;
import java.util.List;

public interface FeedbackThreeSixtyService {
    FeedbackThreeSixty createFeedback(FeedbackThreeSixtyDTO dto);
    FeedbackThreeSixty updateFeedback(Long id, FeedbackThreeSixtyDTO dto);
    void softDeleteFeedback(Long id);
    void hardDeleteFeedback(Long id);
    FeedbackThreeSixty getFeedbackById(Long id);
    List<FeedbackThreeSixty> getFeedbacksByReviewee(Long revieweeId);
    List<FeedbackThreeSixty> getAllActiveFeedbacks();
}
