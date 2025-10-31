package com.example.User.repository;

import com.example.User.entity.EmployeePayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeePayrollRepository extends JpaRepository<EmployeePayroll, Long> {
    List<EmployeePayroll> findByEmployee_EmployeeIdAndIsActiveTrue(Long employeeId);
    List<EmployeePayroll> findByIsActiveTrue();
}
