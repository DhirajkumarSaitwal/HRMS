package com.example.User.service;



import com.example.User.dto.LeaveRequestDto;
import com.example.User.dto.LeaveResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LeaveService {

    LeaveResponseDto applyLeave(LeaveRequestDto request, MultipartFile file);

    List<LeaveResponseDto> getLeavesByEmployee(Long employeeId);
    LeaveResponseDto getLeaveById(Long leaveId);
    LeaveResponseDto approveOrReject(Long leaveId, boolean approve,  String comment);
    LeaveResponseDto cancelLeave(Long leaveId, Long requesterId);

    List<LeaveResponseDto> getAllLeaves();
}
