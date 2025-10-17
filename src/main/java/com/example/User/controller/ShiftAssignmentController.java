package com.example.User.controller;

import com.example.User.dto.ShiftAssignmentDTO;
import com.example.User.service.ShiftAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftAssignmentController {
    @Autowired
    private  ShiftAssignmentService assignmentService;


   @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    @PostMapping("/assign")
    public ResponseEntity<ShiftAssignmentDTO> assign(@Valid @RequestBody ShiftAssignmentDTO dto) {
        return ResponseEntity.ok(assignmentService.assignShift(dto));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")
    public ResponseEntity<List<ShiftAssignmentDTO>> getForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsForEmployee(employeeId));
    }



}
