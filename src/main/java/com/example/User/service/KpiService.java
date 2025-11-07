package com.example.User.service;

import com.example.User.dto.KpiMasterDto;

public interface KpiService {
    KpiMasterDto createKpi(KpiMasterDto dto);
    KpiMasterDto updateKpi(Long id, KpiMasterDto dto);
}
