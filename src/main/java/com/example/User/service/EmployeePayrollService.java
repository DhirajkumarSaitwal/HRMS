package com.example.User.service;

import com.example.User.dto.EmployeePayrollDTO;
import com.example.User.entity.EmployeePayroll;
import java.util.List;

public interface EmployeePayrollService {
    EmployeePayrollDTO createPayroll(EmployeePayrollDTO dto);
    List<EmployeePayroll> getAllPayrolls();
    EmployeePayrollDTO getPayrollById(Long id);
    EmployeePayrollDTO updatePayroll(Long id, EmployeePayrollDTO dto);
    void deletePayroll(Long id);

}
