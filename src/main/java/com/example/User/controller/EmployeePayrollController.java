package com.example.User.controller;

import com.example.User.dto.EmployeePayrollRequestDTO;
import com.example.User.dto.EmployeePayrollResponseDTO;
import com.example.User.entity.EmployeePayroll;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class EmployeePayrollController {

    @Autowired
    private EmployeePayrollService employeePayrollService;

    @PostMapping("/process")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayroll> processPayroll(@RequestBody EmployeePayroll payroll) {
        EmployeePayroll savedPayroll = employeePayrollService.processPayroll(payroll);
        return new ResponseEntity<>(savedPayroll, HttpStatus.CREATED);


    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<EmployeePayrollResponseDTO>> getAllPayrolls() {
        List<EmployeePayrollResponseDTO> payrolls = employeePayrollService.getAllPayrolls();
        if (payrolls.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayrollResponseDTO> getPayrollById(@PathVariable Long id) {
        EmployeePayrollResponseDTO response = employeePayrollService.getPayrollById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<EmployeePayroll>> getPayrollByEmployeeId(@PathVariable Long employeeId) {
        List<EmployeePayroll> payrolls = employeePayrollService.getPayrollsByEmployeeId(employeeId);
        return ResponseEntity.ok(payrolls);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayrollResponseDTO> updatePayroll(
            @PathVariable Long id,
            @RequestBody EmployeePayrollRequestDTO payrollRequest) {
        EmployeePayrollResponseDTO updatedPayroll = employeePayrollService.updatePayroll(id, payrollRequest);
        return ResponseEntity.ok(updatedPayroll);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
        employeePayrollService.deletePayroll(id);
        return ResponseEntity.ok("Payroll record deleted successfully with ID: " + id);
    }
}
