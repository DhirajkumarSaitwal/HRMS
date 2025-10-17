package com.example.User.service;

import com.example.User.dto.EmployeeComplianceMappingDTO;
import java.util.List;

public interface EmployeeComplianceMappingService {
    EmployeeComplianceMappingDTO assignCompliance(EmployeeComplianceMappingDTO dto);
    List<EmployeeComplianceMappingDTO> getEmployeeCompliances(Long employeeId);
}
