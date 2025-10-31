package com.example.User.service;
import com.example.User.entity.EmployeePayroll;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeePayrollService {

    EmployeePayroll processPayroll(EmployeePayroll payroll);

    EmployeePayroll updatePayroll(Long id, EmployeePayroll payroll);

    EmployeePayroll markAsProcessed(Long id, String bankRefNo);

    List<EmployeePayroll> getAllPayrolls();

    List<EmployeePayroll> getPayrollsByEmployee(Long employeeId);

    EmployeePayroll getPayrollById(Long id);

    void deletePayroll(Long id);
}
