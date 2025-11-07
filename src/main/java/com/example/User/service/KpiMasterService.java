package com.example.User.service;
import com.example.User.entity.KpiMaster;
import java.util.List;
public interface KpiMasterService {
    KpiMaster createKpi(KpiMaster kpiMaster);
    KpiMaster updateKpi(Long id, KpiMaster kpiMaster);
    List<KpiMaster> getAllKpis();
    KpiMaster getKpiById(Long id);
    void softDeleteKpi(Long id);
    void hardDeleteKpi(Long id);
}
