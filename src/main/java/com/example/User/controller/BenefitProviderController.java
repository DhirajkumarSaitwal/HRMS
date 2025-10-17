package com.example.User.controller;

import com.example.User.entity.BenefitProvider;
import com.example.User.service.BenefitProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class BenefitProviderController {
@Autowired
    private  BenefitProviderService providerService;

    public BenefitProviderController(BenefitProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<BenefitProvider> createProvider(@RequestBody BenefitProvider provider) {
        return ResponseEntity.ok(providerService.createProvider(provider));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<BenefitProvider> updateProvider(@PathVariable Long id, @RequestBody BenefitProvider provider) {
        return ResponseEntity.ok(providerService.updateProvider(id, provider));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.ok("Provider deleted successfully");
    }

    @GetMapping
    @PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")
    public ResponseEntity<List<BenefitProvider>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")
    public ResponseEntity<BenefitProvider> getProviderById(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.getProviderById(id));
    }
}

