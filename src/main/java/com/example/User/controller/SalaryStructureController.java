package com.example.User.controller;
import com.example.User.dto.SalaryStructureDTO;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payroll/structure")
@CrossOrigin("*")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService salaryStructureService;

    //  CREATE API
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public SalaryStructureDTO create(@RequestBody SalaryStructureDTO dto) {
        return salaryStructureService.createSalaryStructure(dto);
    }

    //  GET ALL API
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<SalaryStructureMaster> getAllStructures() {
        return salaryStructureService.getAllStructures();
    }

    // GET BY ID API
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public SalaryStructureDTO getById(@PathVariable Long id) {
        return salaryStructureService.getById(id);
    }

    // UPDATE API
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public SalaryStructureDTO update(@PathVariable Long id, @RequestBody SalaryStructureDTO dto) {
        return salaryStructureService.updateSalaryStructure(id, dto);
    }

    // HARD DELETE API
    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> hardDeleteSalaryStructure(@PathVariable("id") Long id) {
        salaryStructureService.deleteSalaryStructure(id);
        return ResponseEntity.ok("Salary Structure with ID " + id + " permanently deleted from database.");
    }

    // SOFT DELETE API
    @PutMapping("/soft-delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> softDeleteSalaryStructure(@PathVariable("id") Long id) {
        salaryStructureService.softDeleteSalaryStructure(id);
        return ResponseEntity.ok("Salary Structure with ID " + id + " soft deleted successfully (marked inactive).");
    }
}
