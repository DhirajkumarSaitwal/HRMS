package com.example.User.mapper;


import com.example.User.dto.HolidayRequestDTO;
import com.example.User.dto.HolidayResponseDTO;
import com.example.User.entity.HolidayCalendar;
import com.example.User.entity.HolidayType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class HolidayMapper {

    public HolidayCalendar toEntity(HolidayRequestDTO dto) {
        HolidayCalendar entity = new HolidayCalendar();
        entity.setHolidayName(dto.getHolidayName());
        entity.setHolidayDate(dto.getHolidayDate());
        entity.setRegion(dto.getRegion());
        entity.setIsOptional(dto.getIsOptional());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedBy("admin");
        entity.setUpdatedBy("admin");

        // ✅ Convert String to Enum safely
        if (dto.getHolidayType() != null) {
            try {
                entity.setHolidayType(HolidayType.valueOf(dto.getHolidayType().trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid holidayType value: " + dto.getHolidayType()
                        + ". Allowed values: NATIONAL, FESTIVAL, OPTIONAL, CUSTOM");
            }
        } else {
            entity.setHolidayType(null);
        }

        entity.setDescription(dto.getDescription());
        return entity;
    }

    public HolidayResponseDTO toResponseDTO(HolidayCalendar entity) {
        HolidayResponseDTO dto = new HolidayResponseDTO();
        dto.setHolidayId(entity.getHolidayId());
        dto.setHolidayName(entity.getHolidayName());
        dto.setHolidayDate(entity.getHolidayDate());
        dto.setRegion(entity.getRegion());
        dto.setIsOptional(entity.getIsOptional());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        // ✅ Convert Enum to String for DTO
        if (entity.getHolidayType() != null) {
            dto.setHolidayType(entity.getHolidayType().name());
        }

        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }
}



