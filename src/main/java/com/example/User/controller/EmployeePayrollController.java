package com.example.User.controller;
import com.example.User.entity.EmployeePayroll;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin("*")
public class EmployeePayrollController {

    @Autowired
    private EmployeePayrollService payrollService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayroll> createPayroll(@RequestBody EmployeePayroll payroll) {
        return ResponseEntity.ok(payrollService.createEmployeePayroll(payroll));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','EMPLOYEE')")
    public ResponseEntity<EmployeePayroll> getPayrollById(@PathVariable Long id) {
        return ResponseEntity.ok(payrollService.getEmployeePayrollById(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<EmployeePayroll>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllEmployeePayrolls());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayroll> updatePayroll(@PathVariable Long id, @RequestBody EmployeePayroll payroll) {
        return ResponseEntity.ok(payrollService.updateEmployeePayroll(id, payroll));
    }

    @PutMapping("/softDelete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        payrollService.softDeleteEmployeePayroll(id);
        return ResponseEntity.ok("Payroll record soft deleted successfully.");
    }

    @DeleteMapping("/hardDelete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> hardDelete(@PathVariable Long id) {
        payrollService.hardDeleteEmployeePayroll(id);
        return ResponseEntity.ok("Payroll record permanently deleted.");
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','EMPLOYEE')")
    public ResponseEntity<List<EmployeePayroll>> getPayrollByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(payrollService.getPayrollByEmployeeId(employeeId));
    }
}
