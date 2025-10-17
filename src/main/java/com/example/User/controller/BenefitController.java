package com.example.User.controller;

import com.example.User.entity.BenefitMaster;
import com.example.User.service.BenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<BenefitMaster> createBenefit(@RequestBody BenefitMaster benefit) {
        return ResponseEntity.ok(benefitService.createBenefit(benefit));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<BenefitMaster> updateBenefit(@PathVariable Long id, @RequestBody BenefitMaster benefit) {
        return ResponseEntity.ok(benefitService.updateBenefit(id, benefit));
    }

    @GetMapping
    @PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")

    public ResponseEntity<List<BenefitMaster>> getAllBenefits() {
        return ResponseEntity.ok(benefitService.getAllBenefits());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBenefit(@PathVariable Long id) {
        benefitService.deleteBenefit(id);
        return ResponseEntity.ok("Benefit deleted successfully");
    }


}

