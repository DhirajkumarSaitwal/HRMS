package com.example.User.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FeedbackThreeSixtyDTO {
    private Long reviewerId;
    private Long revieweeId;
    private String comments;
    private BigDecimal rating;
}
