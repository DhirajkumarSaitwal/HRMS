package com.example.User.serviceimpl;

import com.example.User.dto.EmployeeComplianceMappingResponse;
import com.example.User.entity.EmployeeComplianceMapping;
import com.example.User.repository.EmployeeComplianceMappingRepository;
import com.example.User.service.EmployeeComplianceMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeComplianceMappingServiceImpl implements EmployeeComplianceMappingService {

    private final EmployeeComplianceMappingRepository repository;

    @Override
    public EmployeeComplianceMapping createMapping(EmployeeComplianceMapping mapping) {
        // You can add duplicate checks or validations here if needed

        return repository.save(mapping);
    }
    @Override
    public List<EmployeeComplianceMappingResponse> getAllMappings() {
        List<EmployeeComplianceMapping> list = repository.findAll();
        return list.stream()
                .map(EmployeeComplianceMappingResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeComplianceMappingResponse> getMappingsByEmployee(Long employeeId) {
        List<EmployeeComplianceMapping> list = repository.findByEmployeeId(employeeId);
        return list.stream()
                .map(EmployeeComplianceMappingResponse::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteMappingById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Mapping not found with id: " + id);
        }
        repository.deleteById(id);
    }
    @Override
    public EmployeeComplianceMapping updateMapping(Long id, EmployeeComplianceMapping mappingDetails) {
        EmployeeComplianceMapping existingMapping = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping not found with id: " + id));

        existingMapping.setEmployeeId(mappingDetails.getEmployeeId());
        existingMapping.setComplianceId(mappingDetails.getComplianceId());




        return repository.save(existingMapping);
    }
}
