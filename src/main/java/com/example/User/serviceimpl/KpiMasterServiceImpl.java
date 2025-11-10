package com.example.User.serviceimpl;

import com.example.User.dto.KpiMasterDto;
import com.example.User.entity.Department;
import com.example.User.entity.KpiMaster;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.DepartmentRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.service.KpiMasterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KpiMasterServiceImpl implements KpiMasterService {

    @Autowired
    private KpiMasterRepository kpiMasterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public KpiMasterDto createKpi(KpiMasterDto dto) {
        Optional<KpiMaster> existingKpi = kpiMasterRepository
                .findByDepartment_DepartmentIdAndRoleAndKpiName(
                        dto.getDepartmentId(), dto.getRole(), dto.getKpiName());

        if (existingKpi.isPresent()) {
            throw new ResourceNotFoundException("KPI already exists for this Department and Role");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        KpiMaster kpi = KpiMaster.builder()
                .department(department)
                .role(dto.getRole())
                .kpiName(dto.getKpiName())
                .kpiDescription(dto.getKpiDescription())
                .weightage(dto.getWeightage())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        KpiMaster saved = kpiMasterRepository.save(kpi);
        return mapToDto(saved);
    }

    @Override
    public List<KpiMasterDto> getAllKpis() {
        return kpiMasterRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public KpiMasterDto getKpiById(Long kpiId) {
        KpiMaster kpi = kpiMasterRepository.findById(kpiId)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found with ID: " + kpiId));
        return mapToDto(kpi);
    }

    @Override
    public KpiMasterDto updateKpi(Long kpiId, KpiMasterDto dto) {
        KpiMaster existingKpi = kpiMasterRepository.findById(kpiId)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found with ID: " + kpiId));

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + dto.getDepartmentId()));
            existingKpi.setDepartment(department);
        }

        if (dto.getRole() != null) existingKpi.setRole(dto.getRole());
        if (dto.getKpiName() != null) existingKpi.setKpiName(dto.getKpiName());
        if (dto.getKpiDescription() != null) existingKpi.setKpiDescription(dto.getKpiDescription());
        if (dto.getWeightage() != null) existingKpi.setWeightage(dto.getWeightage());

        existingKpi.setUpdatedAt(LocalDateTime.now());
        KpiMaster updated = kpiMasterRepository.save(existingKpi);
        return mapToDto(updated);
    }

    @Override
    public void deleteKpi(Long kpiId) {
        KpiMaster existingKpi = kpiMasterRepository.findById(kpiId)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found with ID: " + kpiId));
        existingKpi.setIsActive(false);
        existingKpi.setUpdatedAt(LocalDateTime.now());
        kpiMasterRepository.save(existingKpi);
    }

    @Override
    public void hardDeleteKpi(Long kpiId) {
        KpiMaster existingKpi = kpiMasterRepository.findById(kpiId)
                .orElseThrow(() -> new ResourceNotFoundException("KPI not found with ID: " + kpiId));
        kpiMasterRepository.delete(existingKpi);
    }

    private KpiMasterDto mapToDto(KpiMaster kpi) {
        return KpiMasterDto.builder()
                .kpiId(kpi.getKpiId())
                .departmentId(kpi.getDepartment().getDepartmentId())
                .role(kpi.getRole())
                .kpiName(kpi.getKpiName())
                .kpiDescription(kpi.getKpiDescription())
                .weightage(kpi.getWeightage())
                .build();
    }
}
