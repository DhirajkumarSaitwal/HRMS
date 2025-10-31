package com.example.User.controller;

import com.example.User.dto.SalaryStructureRequestDTO;
import com.example.User.dto.SalaryStructureResponseDTO;
import com.example.User.entity.SalaryStructure;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payroll/structure")
public class SalaryStructureController {
    @Autowired
    private SalaryStructureService salaryStructureService;

    // create Salary Structure (HR/Admin only)

    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<SalaryStructureResponseDTO> createStructure(@RequestBody SalaryStructureRequestDTO requestDto) {
        SalaryStructureResponseDTO response = salaryStructureService.createSalaryStructure(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<SalaryStructureResponseDTO> updateStructure(
            @PathVariable Long id,
            @RequestBody SalaryStructureRequestDTO requestDto) {

        SalaryStructureResponseDTO response = salaryStructureService.updateSalaryStructure(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<SalaryStructure>> getAllSalaryStructures() {
        List<SalaryStructure> structures = salaryStructureService.getAllSalaryStructures();
        if (structures.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(structures);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<SalaryStructure> getSalaryStructureById(@PathVariable Long id) {
        SalaryStructure structure = salaryStructureService.getSalaryStructureById(id);
        return ResponseEntity.ok(structure);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> deleteSalaryStructure(@PathVariable("id") Long id) {
        salaryStructureService.deleteSalaryStructure(id);
        return ResponseEntity.ok("Salary Structure deleted successfully with ID: " + id);
    }
}

