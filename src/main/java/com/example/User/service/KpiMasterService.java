package com.example.User.service;

import com.example.User.dto.KpiMasterDto;

import java.util.List;

public interface KpiMasterService {
    KpiMasterDto createKpi(KpiMasterDto kpiMasterDto);
    List<KpiMasterDto> getAllKpis();
    KpiMasterDto getKpiById(Long kpiId);
    KpiMasterDto updateKpi(Long kpiId, KpiMasterDto kpiMasterDto);
    void deleteKpi(Long kpiId);
    void hardDeleteKpi(Long kpiId);








}
