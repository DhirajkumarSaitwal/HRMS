package com.example.User.controller;


import com.example.User.dto.LeaveRequestDto;
import com.example.User.dto.LeaveResponseDto;
import com.example.User.service.LeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;

    private final ObjectMapper objectMapper;

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LeaveResponseDto> applyLeave(
            @RequestPart("leave") String leaveJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {

        LeaveRequestDto request = objectMapper.readValue(leaveJson, LeaveRequestDto.class);

        LeaveResponseDto response = leaveService.applyLeave(request, file);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{employeeId}/list")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','HR','ADMIN')")
    public ResponseEntity<List<LeaveResponseDto>> listByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getLeavesByEmployee(employeeId));
    }

    @GetMapping("/{leaveId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','HR','ADMIN')")
    public ResponseEntity<LeaveResponseDto> getById(@PathVariable Long leaveId) {
        return ResponseEntity.ok(leaveService.getLeaveById(leaveId));
    }

    @PutMapping("/approve/{leaveId}")
    @PreAuthorize("hasAnyRole('MANAGER','HR','ADMIN')")
    public ResponseEntity<LeaveResponseDto> approve(@PathVariable Long leaveId,
                                                    @RequestParam boolean approve,
                                                    @RequestParam(required = false) String comment
                                                    ) {
        return ResponseEntity.ok(leaveService.approveOrReject(leaveId, approve, comment));
    }

    @PutMapping("/cancel/{leaveId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveResponseDto> cancel(@PathVariable Long leaveId, @RequestParam Long requesterId) {
        return ResponseEntity.ok(leaveService.cancelLeave(leaveId, requesterId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<LeaveResponseDto>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }
}
