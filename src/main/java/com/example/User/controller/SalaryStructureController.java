package com.example.User.controller;

import com.example.User.entity.SalaryStructure;
import com.example.User.service.SalaryStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payroll/structure")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructure> createSalaryStructure(@Valid @RequestBody SalaryStructure structure) {
        return ResponseEntity.ok(service.createSalaryStructure(structure));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructure> updateSalaryStructure(@PathVariable Long id, @Valid @RequestBody SalaryStructure structure) {
        return ResponseEntity.ok(service.updateSalaryStructure(id, structure));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<SalaryStructure>> getAllSalaryStructures() {
        return ResponseEntity.ok(service.getAllSalaryStructures());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructure> getSalaryStructureById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSalaryStructureById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<String> deleteSalaryStructure(@PathVariable Long id) {
        service.deleteSalaryStructure(id);
        return ResponseEntity.ok("Salary structure deleted successfully");
    }
}
