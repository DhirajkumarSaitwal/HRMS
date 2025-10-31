package com.example.User.controller;

import com.example.User.dto.EmployeePayrollDTO;
import com.example.User.service.EmployeePayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employeePayroll")
@RequiredArgsConstructor

public class EmployeePayrollController {

    @Autowired
    private EmployeePayrollService payrollService;

    @PostMapping("/create")
    public EmployeePayrollDTO create(@RequestBody EmployeePayrollDTO dto) {
        return payrollService.create(dto);
    }

    @GetMapping("/all")
    public List<EmployeePayrollDTO> getAllPayroll() {
        return payrollService.getAll();
    }

    @GetMapping("/{id}")
    public EmployeePayrollDTO getByPayrollId(@PathVariable Long id) {
        return payrollService.getById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeePayrollDTO> getByEmployee(@PathVariable Long employeeId) {
        return payrollService.getByEmployee(employeeId);
    }

    @GetMapping("/structure/{structureId}")
    public List<EmployeePayrollDTO> getByStructure(@PathVariable Long structureId) {
        return payrollService.getByStructure(structureId);
    }

    // ✅ Get Active Payroll by Structure ID
    @GetMapping("/structure/{structureId}/active")
    public List<EmployeePayrollDTO> getActiveByStructure(@PathVariable Long structureId) {
        return payrollService.getByStructureActive(structureId);
    }

    // ✅ Get Inactive Payroll by Structure ID
    @GetMapping("/structure/{structureId}/inactive")
    public List<EmployeePayrollDTO> getInactiveByStructure(@PathVariable Long structureId) {
        return payrollService.getByStructureInactive(structureId);
    }

    @PutMapping("/update/{id}")
    public EmployeePayrollDTO update(@PathVariable Long id, @RequestBody EmployeePayrollDTO dto) {
        return payrollService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        payrollService.delete(id);
        return "Payroll deleted successfully!";
    }


    @PutMapping("/restore/{id}")
    public EmployeePayrollDTO restore(@PathVariable Long id) {
        return payrollService.restore(id);
    }
}
