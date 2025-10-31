package com.example.User.service;

import com.example.User.dto.EmployeePayrollDTO;
import java.util.List;

public interface EmployeePayrollService {

    EmployeePayrollDTO create(EmployeePayrollDTO dto);
    List<EmployeePayrollDTO> getAll();
    EmployeePayrollDTO getById(Long id);
    List<EmployeePayrollDTO> getByEmployee(Long employeeId);
    List<EmployeePayrollDTO> getByStructure(Long structureId);
    List<EmployeePayrollDTO> getByStructureActive(Long structureId);
    List<EmployeePayrollDTO> getByStructureInactive(Long structureId);
    EmployeePayrollDTO update(Long id, EmployeePayrollDTO dto);
    void delete(Long id);
    EmployeePayrollDTO restore(Long id);

}
