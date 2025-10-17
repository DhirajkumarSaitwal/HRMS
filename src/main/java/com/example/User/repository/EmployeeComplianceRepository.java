package com.example.User.repository;

import com.example.User.entity.EmployeeComplianceMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeComplianceRepository extends
        JpaRepository<EmployeeComplianceMapping, String> {
    List<EmployeeComplianceMapping> findByEmployeeId(Long employeeId);
    Optional<EmployeeComplianceMapping> findByEmployeeIdAndComplianceIdAndStatus
            (Long empId, Long compId, EmployeeComplianceMapping.Status status);
}
