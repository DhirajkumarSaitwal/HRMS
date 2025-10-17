package com.example.User.controller;

import com.example.User.dto.EmployeeComplianceMappingDTO;
import com.example.User.service.EmployeeComplianceMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance/employee")
public class EmployeeComplianceMappingController {

    @Autowired
    private EmployeeComplianceMappingService service;

    // Assign compliance to employee
    @PostMapping
    public ResponseEntity<?> assignCompliance(@RequestBody EmployeeComplianceMappingDTO dto) {
        try {
            return ResponseEntity.ok(service.assignCompliance(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all compliances for one employee
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<EmployeeComplianceMappingDTO>> getCompliances(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getEmployeeCompliances(employeeId));
    }


    }
