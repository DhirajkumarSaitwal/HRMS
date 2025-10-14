package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayResponseDTO {
    private Long holidayId;
    private String holidayName;
    private LocalDate holidayDate;
    private String region;
    private Boolean isOptional;
    private String holidayType;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;


}



