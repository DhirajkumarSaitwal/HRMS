package com.example.User.serviceimpl;
import com.example.User.dto.EmployeeComplianceMappingDTO;
import com.example.User.entity.EmployeeComplianceMapping;
import com.example.User.repository.EmployeeComplianceMappingRepository;
import com.example.User.service.EmployeeComplianceMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeComplianceMappingServiceImpl implements EmployeeComplianceMappingService {

    @Autowired
    private EmployeeComplianceMappingRepository repository;

    @Override
    public EmployeeComplianceMappingDTO assignCompliance(EmployeeComplianceMappingDTO dto) {
        // DTO → Entity
        EmployeeComplianceMapping entity = EmployeeComplianceMapping.builder()
                .employeeId(dto.getEmployeeId())
                .complianceId(dto.getComplianceId())
                .complianceNumber(dto.getComplianceNumber())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(EmployeeComplianceMapping.Status.valueOf(dto.getStatus()))
                .remarks(dto.getRemarks())
                .createdBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : "System")
                .updatedBy(null)
                .build();

        // Save in DB
        EmployeeComplianceMapping saved = repository.save(entity);

        // Entity → DTO (response)
        return mapToDTO(saved);
    }

    @Override
    public List<EmployeeComplianceMappingDTO> getEmployeeCompliances(Long employeeId) {
        return repository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper method: Entity → DTO
    private EmployeeComplianceMappingDTO mapToDTO(EmployeeComplianceMapping entity) {
        return EmployeeComplianceMappingDTO.builder()
                .complianceMappingId(entity.getComplianceMappingId())
                .employeeId(entity.getEmployeeId())
                .complianceId(entity.getComplianceId())
                .complianceNumber(entity.getComplianceNumber())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus().name())
                .remarks(entity.getRemarks())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
