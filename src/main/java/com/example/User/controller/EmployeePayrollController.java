package com.example.User.controller;

import com.example.User.entity.EmployeePayroll;
import com.example.User.service.EmployeePayrollService;
import jakarta.validation.Valid;
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

    @PostMapping("/process")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayroll> processPayroll(@Valid @RequestBody EmployeePayroll payroll) {
        return ResponseEntity.ok(service.processPayroll(payroll));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayroll> updatePayroll(@PathVariable Long id, @Valid @RequestBody EmployeePayroll payroll) {
        return ResponseEntity.ok(service.updatePayroll(id, payroll));
    }

    @PutMapping("/markProcessed/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayroll> markAsProcessed(@PathVariable Long id, @RequestParam String bankRefNo) {
        return ResponseEntity.ok(service.markAsProcessed(id, bankRefNo));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<EmployeePayroll>> getAllPayrolls() {
        return ResponseEntity.ok(service.getAllPayrolls());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<EmployeePayroll> getPayrollById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPayrollById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<EmployeePayroll>> getPayrollsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getPayrollsByEmployee(employeeId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
        service.deletePayroll(id);
        return ResponseEntity.ok("Payroll deleted successfully");
    }
}
