package com.example.User.serviceimpl;

import com.example.User.dto.ShiftAssignmentDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.ShiftAssignment;
import com.example.User.entity.ShiftMaster;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.ConflictException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.ShiftAssignmentRepository;
import com.example.User.repository.ShiftMasterRepository;
import com.example.User.service.ShiftAssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftAssignmentServiceImpl implements ShiftAssignmentService {
    @Autowired
    private  ShiftAssignmentRepository assignmentRepo;
    @Autowired
    private ShiftMasterRepository shiftRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    @Transactional
    public ShiftAssignmentDTO assignShift(ShiftAssignmentDTO dto) {
        LocalDate today = LocalDate.now();

        if (dto.getEffectiveFromDate().isAfter(dto.getEffectiveToDate())) {
            throw new BadRequestException("effectiveFromDate must be <= effectiveToDate");
        }

        if (dto.getEffectiveFromDate().isBefore(today)) {
            throw new BadRequestException("effectiveFromDate cannot be in the past");
        }

        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new BadRequestException("Employee not found with ID: " + dto.getEmployeeId()));

        ShiftMaster shift = shiftRepo.findById(dto.getShiftId())
                .orElseThrow(() -> new BadRequestException("Shift not found"));

        List<ShiftAssignment> overlapping = assignmentRepo.findOverlappingAssignments(
                dto.getEmployeeId(), dto.getEffectiveFromDate(), dto.getEffectiveToDate());

        if (!overlapping.isEmpty()) {
            throw new ConflictException("Employee already has a shift assigned in the given date range");
        }
        ShiftAssignment entity = ShiftAssignment.builder()
                .employeeId(dto.getEmployeeId())
                .shift(shift)
                .effectiveFromDate(dto.getEffectiveFromDate())
                .effectiveToDate(dto.getEffectiveToDate())
                .assignedBy(dto.getAssignedBy())
                .isActive(true)
                .createdBy("Admin")
                .updatedBy("Admin")
                .assignedBy("Hr")
                .build();

        ShiftAssignment saved = assignmentRepo.save(entity);
        return mapToDto(saved);
    }



    private ShiftAssignmentDTO mapToDto(ShiftAssignment entity) {
        if (entity == null) return null;

        ShiftAssignmentDTO dto = new ShiftAssignmentDTO();
        dto.setShiftId(entity.getShiftAssignmentId());
        dto.setEmployeeId(entity.getEmployeeId());


        if (entity.getShift() != null) {
            dto.setShiftAssignmentId(entity.getShiftAssignmentId());
            dto.setShiftId(entity.getShift().getShiftId());
            dto.setShiftName(entity.getShift().getShiftName());
            dto.setStartTime(entity.getShift().getStartTime());
            dto.setEndTime(entity.getShift().getEndTime());
            dto.setBreakMinutes(entity.getShift().getBreakMinutes());
            dto.setGraceMinutes(entity.getShift().getGraceMinutes());
            dto.setIsRotational(entity.getShift().getIsRotational());
            dto.setWeekOff(entity.getShift().getWeekOff());
        }

        dto.setEffectiveFromDate(entity.getEffectiveFromDate());
        dto.setEffectiveToDate(entity.getEffectiveToDate());
        dto.setAssignedBy(entity.getAssignedBy());
        dto.setIsActive(entity.getIsActive());

        return dto;
    }

    @Override
    public List<ShiftAssignmentDTO> getAssignmentsForEmployee(Long employeeId) {
        List<ShiftAssignment> assignments = assignmentRepo.findByEmployeeIdAndIsActiveTrue(employeeId);

        return assignments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }




}
