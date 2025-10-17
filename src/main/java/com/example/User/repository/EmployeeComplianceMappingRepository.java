package com.example.User.repository;

import com.example.User.entity.EmployeeComplianceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeComplianceMappingRepository extends JpaRepository<EmployeeComplianceMapping, Long> {
    List<EmployeeComplianceMapping> findByEmployeeId(Long employeeId);
}
