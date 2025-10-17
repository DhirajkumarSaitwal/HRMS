package com.example.User.service;

import com.example.User.dto.ShiftAssignmentDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ShiftAssignmentService {
    ShiftAssignmentDTO assignShift(ShiftAssignmentDTO dto);
    List<ShiftAssignmentDTO> getAssignmentsForEmployee(Long employeeId);


}
