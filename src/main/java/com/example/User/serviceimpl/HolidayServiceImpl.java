package com.example.User.serviceimpl;

import com.example.User.dto.HolidayRequestDTO;
import com.example.User.dto.HolidayResponseDTO;
import com.example.User.entity.HolidayCalendar;
import com.example.User.entity.HolidayType;
import com.example.User.exception.DuplicateResourceException;
import com.example.User.mapper.HolidayMapper;
import com.example.User.repository.HolidayRepository;
import com.example.User.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public HolidayResponseDTO addHoliday(HolidayRequestDTO requestDTO) {
        // Validation: Duplicate check
        holidayRepository.findByHolidayDateAndRegion(requestDTO.getHolidayDate(), requestDTO.getRegion())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Holiday already exists for this date and region");
                });

        if (requestDTO.getHolidayName() == null || requestDTO.getHolidayDate() == null || requestDTO.getHolidayType() == null) {
            throw new IllegalArgumentException("Holiday name, date, and type are required");
        }

        if (requestDTO.getHolidayDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Holiday date cannot be in the past");
        }

        HolidayCalendar entity = holidayMapper.toEntity(requestDTO);
        HolidayCalendar savedEntity = holidayRepository.save(entity);

        return holidayMapper.toResponseDTO(savedEntity);
    }


    @Override
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public HolidayResponseDTO updateHoliday(Long holidayId, HolidayRequestDTO request) {
        HolidayCalendar holiday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        // Prevent duplicate on update
        boolean duplicate = holidayRepository.existsByHolidayDateAndRegionAndHolidayIdNot(
                request.getHolidayDate(), request.getRegion(), holidayId);
        if (duplicate) {
            throw new DuplicateResourceException("Another holiday already exists for this date and region");
        }

        // Update fields
        holiday.setHolidayName(request.getHolidayName());
        holiday.setHolidayDate(request.getHolidayDate());
        holiday.setRegion(request.getRegion());
        holiday.setIsOptional(request.getIsOptional());
        holiday.setHolidayType(HolidayType.valueOf(request.getHolidayType().toUpperCase()));
        holiday.setDescription(request.getDescription());
        holiday.setUpdatedBy("adminUser"); // Or use SecurityContext for current user
        holiday.setUpdatedAt(LocalDateTime.now());

        // ✅ Save and assign to variable
        HolidayCalendar updatedHoliday = holidayRepository.save(holiday);

        // ✅ Convert to DTO
        return holidayMapper.toResponseDTO(updatedHoliday);
    }


    //delete filed
    @Override
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public void deleteHoliday(Long holidayId) {
        HolidayCalendar holiday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found with ID: " + holidayId));

        holidayRepository.delete(holiday);
    }

    // ✅ GET API — get all holidays (filter by year/region)
    @Override
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<HolidayResponseDTO> getAllHolidays(Integer year, String region) {

        List<HolidayCalendar> holidays;

        if (year != null && region != null) {
            holidays = holidayRepository.findByYearAndRegion(year, region);
        } else if (year != null) {
            holidays = holidayRepository.findByYear(year);
        } else if (region != null) {
            holidays = holidayRepository.findByRegion(region);
        } else {
            holidays = holidayRepository.findAll();
        }

        return holidays.stream()
                .map(holidayMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HolidayResponseDTO getHolidayById(Long holidayId) {
        HolidayCalendar holiday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday not found with ID: " + holidayId));

        return holidayMapper.toResponseDTO(holiday);

    }
}












