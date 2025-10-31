package com.example.User.service;

import com.example.User.dto.EmployeePayrollRequestDTO;
import com.example.User.dto.EmployeePayrollResponseDTO;
import com.example.User.entity.EmployeePayroll;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeePayrollService {
    EmployeePayrollResponseDTO createPayroll(EmployeePayrollRequestDTO requestDTO);

    EmployeePayrollResponseDTO getPayrollById(Long payrollId);

    EmployeePayroll processPayroll(EmployeePayroll payroll);

    List<EmployeePayrollResponseDTO> getAllPayrolls();

    void deletePayroll(Long payrollId);

    EmployeePayrollResponseDTO updatePayroll(Long id, EmployeePayrollRequestDTO payrollRequest);

    List<EmployeePayroll> getPayrollsByEmployeeId(Long employeeId);
}
