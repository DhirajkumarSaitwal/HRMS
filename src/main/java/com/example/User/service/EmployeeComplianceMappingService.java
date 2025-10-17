package com.example.User.service;

import com.example.User.dto.EmployeeComplianceMappingResponse;
import com.example.User.entity.EmployeeComplianceMapping;

import java.util.List;

public interface EmployeeComplianceMappingService {
    // Create a new Employee Compliance Mapping
    EmployeeComplianceMapping createMapping(EmployeeComplianceMapping mapping);

    List<EmployeeComplianceMappingResponse> getAllMappings();

    List<EmployeeComplianceMappingResponse> getMappingsByEmployee(Long employeeId);

    void deleteMappingById(Long id);

    EmployeeComplianceMapping updateMapping(Long id, EmployeeComplianceMapping mappingDetails);
}

