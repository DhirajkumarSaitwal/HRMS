package com.example.User.controller;

import com.example.User.dto.EmployeePayrollDTO;
import com.example.User.entity.EmployeePayroll;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payroll/employee")
public class EmployeePayrollController {

    @Autowired
    private EmployeePayrollService service;

    //  Create Payroll
    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayrollDTO> createPayroll(@RequestBody EmployeePayrollDTO dto) {
        EmployeePayrollDTO created = service.createPayroll(dto);
        return ResponseEntity.status(201).body(created);
    }

    // Get All Payroll
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<EmployeePayroll>> getAllPayrolls() {
        List<EmployeePayroll> payrollList = service.getAllPayrolls();
        return ResponseEntity.ok(payrollList);
    }

    //  Get Payroll By ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','EMPLOYEE', 'ADMIN')")
    public ResponseEntity<EmployeePayrollDTO> getPayrollById(@PathVariable Long id) {
        EmployeePayrollDTO dto = service.getPayrollById(id);
        return ResponseEntity.ok(dto);
    }

    //  Update Payroll (PUT)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayrollDTO> updatePayroll(
            @PathVariable Long id,
            @RequestBody EmployeePayrollDTO dto) {

        EmployeePayrollDTO updated = service.updatePayroll(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete Payroll Record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
        service.deletePayroll(id);
        return ResponseEntity.ok(" Payroll record deleted successfully with ID: " + id);
    }
}
