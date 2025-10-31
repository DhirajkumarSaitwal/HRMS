package com.example.User.controller;

import com.example.User.dto.CreateSalaryStructureRequest;
import com.example.User.dto.SalaryStructureDto;
import com.example.User.service.SalaryStructureService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payroll/structure")
public class SalaryStructureController {
    private final SalaryStructureService service;
    public SalaryStructureController(SalaryStructureService service) {
        this.service = service;
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructureDto> create(@Validated @RequestBody CreateSalaryStructureRequest request) {
        SalaryStructureDto created = service.createSalaryStructure(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<SalaryStructureDto>> getAll() {
        List<SalaryStructureDto> list = service.getAllSalaryStructures();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructureDto> getById(@PathVariable("id") Long id) {
        SalaryStructureDto dto = service.getSalaryStructureById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<SalaryStructureDto> update(@PathVariable("id") Long id,
                                                     @Validated @RequestBody CreateSalaryStructureRequest request) {
        SalaryStructureDto updated = service.updateSalaryStructure(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<Map<String, String>> deleteSalaryStructure(@PathVariable Long id) {
        String message = service.deleteSalaryStructure(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }





}
