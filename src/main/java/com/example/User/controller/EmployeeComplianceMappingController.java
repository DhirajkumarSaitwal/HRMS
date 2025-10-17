package com.example.User.controller;

import com.example.User.dto.EmployeeComplianceMappingResponse;
import com.example.User.entity.EmployeeComplianceMapping;
import com.example.User.service.EmployeeComplianceMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/employee")
@RequiredArgsConstructor
public class EmployeeComplianceMappingController {

    private final EmployeeComplianceMappingService service;

    @PostMapping
    public ResponseEntity<EmployeeComplianceMapping> createMapping(@RequestBody EmployeeComplianceMapping mapping) {
        EmployeeComplianceMapping saved = service.createMapping(mapping);
        return ResponseEntity.ok(saved);
    }
    // Get all mappings
    @GetMapping
    public ResponseEntity<List<EmployeeComplianceMappingResponse>> getAllMappings() {
        return ResponseEntity.ok(service.getAllMappings());
    }

    // Get mappings by employee
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<EmployeeComplianceMappingResponse>> getMappingsByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getMappingsByEmployee(employeeId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMapping(@PathVariable Long id) {
        service.deleteMappingById(id);
        return ResponseEntity.ok("Mapping deleted successfully with id: " + id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeComplianceMapping> updateMapping(
            @PathVariable Long id,
            @RequestBody EmployeeComplianceMapping mappingDetails) {

        EmployeeComplianceMapping updatedMapping = service.updateMapping(id, mappingDetails);
        return ResponseEntity.ok(updatedMapping);
    }
}