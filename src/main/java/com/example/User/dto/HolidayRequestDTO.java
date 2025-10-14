package com.example.User.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HolidayRequestDTO {
    private String holidayName;
    private LocalDate holidayDate;
    private String region;
    private Boolean isOptional;
    private String holidayType;
    private String description;

}
