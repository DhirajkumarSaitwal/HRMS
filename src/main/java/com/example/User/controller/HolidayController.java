package com.example.User.controller;


import com.example.User.dto.HolidayFilterRequestDTO;
import com.example.User.dto.HolidayRequestDTO;
import com.example.User.dto.HolidayResponseDTO;
import com.example.User.service.HolidayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    // POST API — Add new holiday
    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")//Adding
    public ResponseEntity<HolidayResponseDTO> addHoliday(@RequestBody HolidayRequestDTO RequestDTO) {
        HolidayResponseDTO response = holidayService.addHoliday(RequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ UPDATE API (PUT)
    @PutMapping("/{holidayId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')") // only HR or Admin can update
    public ResponseEntity<HolidayResponseDTO> updateHoliday(
            @PathVariable Long holidayId,
            @RequestBody @Valid HolidayRequestDTO request) {

        HolidayResponseDTO response = holidayService.updateHoliday(holidayId, request);
        return ResponseEntity.ok(response);
    }

    // ✅ DELETE API — Delete a holiday by ID
    @DeleteMapping("/{holidayId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')") // Only HR/Admin can delete
    public ResponseEntity<String> deleteHoliday(@PathVariable Long holidayId) {
        holidayService.deleteHoliday(holidayId);
        return ResponseEntity.ok("Holiday deleted successfully");
    }

    // ✅ GET API — Get all holidays (filter by year/region)
    @GetMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<List<HolidayResponseDTO>> getAllHolidays(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String region) {

        List<HolidayResponseDTO> holidays = holidayService.getAllHolidays(year, region);
        return ResponseEntity.ok(holidays);
    }

    // ✅ GET API — Get holiday by ID
    @GetMapping("/{holidayId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')") // View-only for Employee/Manager
    public ResponseEntity<HolidayResponseDTO> getHolidayById(@PathVariable Long holidayId) {
        HolidayResponseDTO holiday = holidayService.getHolidayById(holidayId);
        return ResponseEntity.ok(holiday);
    }
}






