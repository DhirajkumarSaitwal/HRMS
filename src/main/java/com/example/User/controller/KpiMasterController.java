package com.example.User.controller;
import com.example.User.entity.KpiMaster;
import com.example.User.service.KpiMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance/kpi")
@CrossOrigin("*")
public class KpiMasterController {

    @Autowired
    private KpiMasterService kpiMasterService;

    // CREATE
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping("/create")
    public KpiMaster createKpi(@RequestBody KpiMaster kpiMaster) {
        return kpiMasterService.createKpi(kpiMaster);
    }

    //  UPDATE
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/update/{id}")
    public KpiMaster updateKpi(@PathVariable Long id, @RequestBody KpiMaster kpiMaster) {
        return kpiMasterService.updateKpi(id, kpiMaster);
    }

    //  GET ALL (Only Active)
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/all")
    public List<KpiMaster> getAllKpis() {
        return kpiMasterService.getAllKpis();
    }

    //  GET BY ID
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/get/{id}")
    public KpiMaster getKpiById(@PathVariable Long id) {
        return kpiMasterService.getKpiById(id);
    }

    // SOFT DELETE
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/soft-delete/{id}")
    public String softDeleteKpi(@PathVariable Long id) {
        kpiMasterService.softDeleteKpi(id);
        return "KPI soft-deleted successfully (isActive = false).";
    }

    //  HARD DELETE
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @DeleteMapping("/hard-delete/{id}")
    public String hardDeleteKpi(@PathVariable Long id) {
        kpiMasterService.hardDeleteKpi(id);
        return "KPI permanently deleted from database.";
    }
}
