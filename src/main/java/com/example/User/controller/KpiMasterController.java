package com.example.User.controller;

import com.example.User.dto.KpiMasterDto;
import com.example.User.service.KpiMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/kpi")
public class KpiMasterController {
    @Autowired
    private KpiMasterService kpiMasterService;

    // Create KPI
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<KpiMasterDto> createKpi(@RequestBody KpiMasterDto dto) {
        KpiMasterDto created = kpiMasterService.createKpi(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get All KPIs
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<KpiMasterDto>> getAllKpis() {
        List<KpiMasterDto> allKpis = kpiMasterService.getAllKpis();
        return new ResponseEntity<>(allKpis, HttpStatus.OK);
    }

    // Get KPI by ID
    @GetMapping("/{kpiId}")
    public ResponseEntity<KpiMasterDto> getKpiById(@PathVariable Long kpiId) {
        System.out.println(" Inside getKpiById() with ID: " + kpiId);
        KpiMasterDto kpi = kpiMasterService.getKpiById(kpiId);
        return new ResponseEntity<>(kpi, HttpStatus.OK);
    }

    // Update KPI
    @PutMapping("/{kpiId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<KpiMasterDto> updateKpi(@PathVariable Long kpiId, @RequestBody KpiMasterDto dto) {
        KpiMasterDto updated = kpiMasterService.updateKpi(kpiId, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Soft Delete KPI (set isActive = false)
    @DeleteMapping("/{kpiId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<String> deleteKpi(@PathVariable Long kpiId) {
        kpiMasterService.deleteKpi(kpiId);
        return new ResponseEntity<>("KPI with ID " + kpiId + " deactivated successfully!", HttpStatus.OK);
    }

    // Hard Delete KPI (permanent removal)
    @DeleteMapping("/hard/{kpiId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<String> hardDeleteKpi(@PathVariable Long kpiId) {
        kpiMasterService.hardDeleteKpi(kpiId);
        return new ResponseEntity<>("KPI with ID " + kpiId + " deleted permanently!", HttpStatus.OK);
    }
}
