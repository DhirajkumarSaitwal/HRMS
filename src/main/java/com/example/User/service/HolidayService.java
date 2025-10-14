package com.example.User.service;


import com.example.User.dto.HolidayFilterRequestDTO;
import com.example.User.dto.HolidayRequestDTO;
import com.example.User.dto.HolidayResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface HolidayService {
    HolidayResponseDTO addHoliday(HolidayRequestDTO requestDTO);


    HolidayResponseDTO updateHoliday(Long holidayId, HolidayRequestDTO request);

    void deleteHoliday(Long holidayId);

// List<HolidayResponseDTO> listHolidays(Integer year, String region);

    List<HolidayResponseDTO> getAllHolidays(Integer year, String region);

    //Get by ID
    HolidayResponseDTO getHolidayById(Long holidayId);


}

