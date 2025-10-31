package com.example.User.controller;

import com.example.User.dto.SalaryStructureDto;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payroll/structure")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService service;

    @PostMapping
    public SalaryStructureDto create(@RequestBody SalaryStructureDto dto) {
        return service.createStructure(dto);
    }

    @GetMapping
    public List<SalaryStructureDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SalaryStructureDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public SalaryStructureDto update(@PathVariable Long id, @RequestBody SalaryStructureDto dto) {
        return service.updateStructure(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteStructure(id);
        return ResponseEntity.ok("Deleted Successfully");
    }


}

