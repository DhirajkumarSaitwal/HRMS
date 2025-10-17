package com.example.User.repository;

import com.example.User.entity.EmployeeComplianceMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeComplianceMappingRepository extends JpaRepository<EmployeeComplianceMapping, Long> {
    // You can add custom queries here if needed later
    List<EmployeeComplianceMapping> findByEmployeeId(Long employeeId);
}
