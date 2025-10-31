package com.example.User.service;
import com.example.User.entity.EmployeePayroll;
import java.util.List;

public interface EmployeePayrollService {

    EmployeePayroll createEmployeePayroll(EmployeePayroll payroll);

    EmployeePayroll getEmployeePayrollById(Long id);

    List<EmployeePayroll> getAllEmployeePayrolls();

    EmployeePayroll updateEmployeePayroll(Long id, EmployeePayroll payroll);

    void softDeleteEmployeePayroll(Long id);

    void hardDeleteEmployeePayroll(Long id);

    List<EmployeePayroll> getPayrollByEmployeeId(Long employeeId);
}
