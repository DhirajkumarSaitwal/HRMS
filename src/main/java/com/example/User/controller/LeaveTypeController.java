package com.example.User.controller;


import com.example.User.entity.LeaveType;
import com.example.User.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

@Autowired
    private LeaveTypeService leaveTypeService;


    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR','MANAGER','ADMIN')")
    public ResponseEntity<List<LeaveType>> getAllLeaveTypes() {
        return ResponseEntity.ok(leaveTypeService.getAllLeaveTypes());
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<LeaveType> addLeaveType(@Validated @RequestBody LeaveType leaveType) {
        LeaveType created = leaveTypeService.createLeaveType(leaveType);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{leaveTypeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<LeaveType> updateLeaveType(@PathVariable Long leaveTypeId,
                                                     @Validated @RequestBody LeaveType leaveType) {
        LeaveType updated = leaveTypeService.updateLeaveType(leaveTypeId, leaveType);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{leaveTypeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long leaveTypeId) {
        leaveTypeService.deleteLeaveType(leaveTypeId);
        return ResponseEntity.noContent().build();
    }
}
