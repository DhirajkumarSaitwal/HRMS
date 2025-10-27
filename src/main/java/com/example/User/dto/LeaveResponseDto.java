package com.example.User.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveResponseDto {
    private Long leaveId;
    private Long employeeId;
    private Long leaveTypeId;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private String reason;
    private String status;
    private Long approverId;
    private String attachment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
