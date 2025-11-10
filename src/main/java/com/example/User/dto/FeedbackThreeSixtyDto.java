package com.example.User.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackThreeSixtyDto {

    private Long feedbackId;        // Auto-generated
    private Long reviewerId;        // FK → employees.employee_id
    private Long revieweeId;        // FK → employees.employee_id
    private String comments;        // Feedback text
    private BigDecimal rating;      // Numeric rating (e.g., 4.75)
    private LocalDateTime createdAt; // Auto timestamp
}
