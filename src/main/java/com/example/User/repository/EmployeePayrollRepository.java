package com.example.User.repository;

import com.example.User.entity.EmployeePayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeePayrollRepository extends JpaRepository<EmployeePayroll, Long> {
    List<EmployeePayroll> findByEmployeeEmployeeId(Long employeeId);

    List<EmployeePayroll> findBySalaryStructureStructureId(Long structureId);
}
