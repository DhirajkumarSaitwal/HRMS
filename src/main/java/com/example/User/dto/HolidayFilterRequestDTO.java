package com.example.User.dto;


import lombok.Data;

@Data
public class HolidayFilterRequestDTO {
    private Integer year;   // Filter by year (optional)
    private String region;  // Filter by region (optional)
}

