package com.example.User.repository;

import com.example.User.entity.EmployeePayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeePayrollRepository extends JpaRepository<EmployeePayroll, Long> {
    Optional<EmployeePayroll> findByEmployeeIdAndMonth(Long employeeId, String month);
    List<EmployeePayroll> findByEmployeeId(Long employeeId);
    boolean existsByEmployeeIdAndMonth(Long employeeId, String month);
}
