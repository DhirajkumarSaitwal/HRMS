package com.example.User.serviceimpl;

import com.example.User.dto.LeaveRequestDto;
import com.example.User.dto.LeaveResponseDto;
import com.example.User.entity.Employee;
import com.example.User.entity.Leave;
import com.example.User.entity.LeaveType;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.ForbiddenException;
import com.example.User.exception.NotFoundException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.LeaveRepository;
import com.example.User.repository.LeaveTypeRepository;
import com.example.User.service.LeaveService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;


    @Override
    public LeaveResponseDto applyLeave(LeaveRequestDto request, MultipartFile file) {
        validateDates(request.getStartDate(), request.getEndDate());

        List<Leave> overlapping = leaveRepository.findOverlappingLeaves(
                request.getEmployeeId(), request.getStartDate(), request.getEndDate()
        );
        if (!overlapping.isEmpty()) {
            throw new BadRequestException("Overlapping leave exists for the selected date range.");
        }
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + request.getEmployeeId()));

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new NotFoundException("Leave type not found"));

        int totalDays = (int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;

        int available = getAvailableBalance(request.getEmployeeId(), leaveType.getLeaveTypeId());
        if (totalDays > available) {
            throw new BadRequestException("Insufficient leave balance. Requested: " + totalDays + ", Available: " + available);
        }


        String fileName = null;
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/leave_attachments/";
                File directory = new File(uploadDir);
                if (!directory.exists()) directory.mkdirs();

                fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("File upload failed", e);
            }
        }

        Leave leave = Leave.builder()
                .employeeId(request.getEmployeeId())
                .leaveType(leaveType)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalDays(totalDays)
                .reason(request.getReason())
                .status(Leave.LeaveStatus.PENDING)
                .attachment(fileName)
                .build();

        Leave saved = leaveRepository.save(leave);
        return mapToDto(saved);
    }

    @Override
    public List<LeaveResponseDto> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDto getLeaveById(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new NotFoundException("Leave not found"));
        return mapToDto(leave);
    }

    @Override
    public LeaveResponseDto approveOrReject(Long leaveId, boolean approve,  String comment) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new NotFoundException("Leave not found"));

        if (leave.getStatus() != Leave.LeaveStatus.PENDING) {
            throw new BadRequestException("Only pending leaves can be approved or rejected.");
        }

        Employee employee = employeeRepository.findById(leave.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        if (approve) {
            if (employee.getReportingManager() != null) {
                leave.setApproverId(employee.getReportingManager().getId());
            } else {
                throw new BadRequestException("Employee does not have a reporting manager assigned.");
            }

            leave.setStatus(Leave.LeaveStatus.APPROVED);
        } else {
            leave.setStatus(Leave.LeaveStatus.REJECTED);
        }
        Leave saved = leaveRepository.save(leave);
        return mapToDto(saved);
    }

    @Override
    public LeaveResponseDto cancelLeave(Long leaveId, Long requesterId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new NotFoundException("Leave not found"));


        if (!leave.getEmployeeId().equals(requesterId)) {
            throw new ForbiddenException("Only the requester can cancel their leave");
        }


        leave.setStatus(Leave.LeaveStatus.CANCELLED);
        Leave saved = leaveRepository.save(leave);
        return mapToDto(saved);
    }



    private void validateDates(java.time.LocalDate start, java.time.LocalDate end) {
        if (end.isBefore(start)) {
            throw new BadRequestException("endDate must be same or after startDate");
        }
    }

    private LeaveResponseDto mapToDto(Leave l) {
        return LeaveResponseDto.builder()
                .leaveId(l.getLeaveId())
                .employeeId(l.getEmployeeId())
                .leaveTypeId(l.getLeaveType() != null ? l.getLeaveType().getLeaveTypeId() : null)
                .leaveTypeName(l.getLeaveType() != null ? l.getLeaveType().getName() : null)
                .startDate(l.getStartDate())
                .endDate(l.getEndDate())
                .totalDays(l.getTotalDays())
                .reason(l.getReason())
                .status(l.getStatus().name())
                .approverId(l.getApproverId())
                .attachment(l.getAttachment())
                .createdAt(l.getCreatedAt())
                .updatedAt(l.getUpdatedAt())
                .build();
    }
    @Override
    public List<LeaveResponseDto> getAllLeaves() {
        return leaveRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    private int getAvailableBalance(@NotNull Long employeeId, Long leaveTypeId) {
        return 30;
    }
}
