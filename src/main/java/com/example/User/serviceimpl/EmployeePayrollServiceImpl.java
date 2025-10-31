package com.example.User.service.impl;

import com.example.User.dto.EmployeePayrollDTO;
import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.Employee;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.EmployeePayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository payrollRepo;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private SalaryStructureRepository structureRepo;

    private EmployeePayrollDTO convertToDTO(EmployeePayroll payroll) {
        EmployeePayrollDTO dto = new EmployeePayrollDTO();

        dto.setPayrollId(payroll.getPayrollId());
        dto.setEmployeeId(payroll.getEmployee().getEmployeeId());
        dto.setStructureId(payroll.getSalaryStructure().getStructureId());
        dto.setMonth(payroll.getMonth());
        dto.setGrossSalary(payroll.getGrossSalary());
        dto.setTotalDeductions(payroll.getTotalDeductions());
        dto.setNetSalary(payroll.getNetSalary());
        dto.setPaymentStatus(payroll.getPaymentStatus());
        dto.setPaymentDate(payroll.getPaymentDate());
        dto.setBankReferenceNo(payroll.getBankReferenceNo());
        dto.setCreatedBy(payroll.getCreatedBy());
        dto.setUpdatedBy(payroll.getUpdatedBy());

        return dto;
    }

    @Override
    public EmployeePayrollDTO create(EmployeePayrollDTO dto) {

        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        SalaryStructureMaster structure = structureRepo.findById(dto.getStructureId())
                .orElseThrow(() -> new RuntimeException("Salary Structure not found"));

        EmployeePayroll payroll = new EmployeePayroll();
        payroll.setEmployee(emp);
        payroll.setSalaryStructure(structure);
        payroll.setMonth(dto.getMonth());
        payroll.setGrossSalary(dto.getGrossSalary());
        payroll.setTotalDeductions(dto.getTotalDeductions());
        payroll.setNetSalary(dto.getNetSalary());
        payroll.setBankReferenceNo("PAY" + System.currentTimeMillis());
        payroll.setPaymentDate(dto.getPaymentDate());

        payroll.setCreatedBy("ADMIN");
        payroll.setUpdatedBy("ADMIN");

        payrollRepo.save(payroll);
        return convertToDTO(payroll);
    }

    @Override
    public List<EmployeePayrollDTO> getAll() {
        return payrollRepo.findAll().stream()
                .filter(EmployeePayroll::getIsActive) //  show active only
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeePayrollDTO getById(Long id) {
        EmployeePayroll payroll = payrollRepo.findById(id)
                .filter(EmployeePayroll::getIsActive)  // ✅ block deleted records
                .orElseThrow(() -> new RuntimeException("Active payroll record not found!"));
        return convertToDTO(payroll);
    }

    @Override
    public List<EmployeePayrollDTO> getByEmployee(Long employeeId) {
        return payrollRepo.findByEmployeeEmployeeId(employeeId).stream()
                .filter(EmployeePayroll::getIsActive) //  Only active
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeePayrollDTO> getByStructure(Long structureId) {
        return payrollRepo.findBySalaryStructureStructureId(structureId)
                .stream()
                .filter(EmployeePayroll::getIsActive) //  Only active records
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeePayrollDTO> getByStructureActive(Long structureId) {
        return payrollRepo.findBySalaryStructureStructureId(structureId)
                .stream()
                .filter(payroll -> Boolean.TRUE.equals(payroll.getIsActive()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeePayrollDTO> getByStructureInactive(Long structureId) {
        return payrollRepo.findBySalaryStructureStructureId(structureId)
                .stream()
                .filter(payroll -> Boolean.FALSE.equals(payroll.getIsActive()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }





//    @Override
//    public EmployeePayrollDTO update(Long id, EmployeePayrollDTO dto) {
//        EmployeePayroll payroll = payrollRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payroll not found"));
//
//
//        payroll.setPaymentStatus(dto.getPaymentStatus());
//        payroll.setPaymentDate(dto.getPaymentDate());
//        payroll.setBankReferenceNo(dto.getBankReferenceNo());
//        payroll.setUpdatedAt(new Date());
//        payroll.setUpdatedBy("ADMIN");
//        payroll.setCreatedBy("ADMIN");
//        payroll.setBankReferenceNo("PAY" + System.currentTimeMillis());
//
//        payrollRepo.save(payroll);
//        return convertToDTO(payroll);
//    }

    @Override
    public EmployeePayrollDTO update(Long id, EmployeePayrollDTO dto) {

        EmployeePayroll payroll = payrollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found!"));

        //  Soft Delete Logic inside Update
        if (dto.getIsActive() != null) {
            payroll.setIsActive(dto.getIsActive());

            if (!dto.getIsActive()) { // if false => mark deleted
                payroll.setUpdatedAt(new Date());
                payroll.setUpdatedBy("ADMIN");
                payrollRepo.save(payroll);
                return convertToDTO(payroll); // stop further update changes
            }
        }

        //  Update Status Logic
        if (dto.getPaymentStatus() != null) {
            payroll.setPaymentStatus(dto.getPaymentStatus());

            if (dto.getPaymentStatus().equalsIgnoreCase("Failed")) {

                payroll.setBankReferenceNo(null);
                payroll.setPaymentDate(null);

            } else if (dto.getPaymentStatus().equalsIgnoreCase("Processed")) {

                payroll.setPaymentDate(dto.getPaymentDate() != null
                        ? dto.getPaymentDate()
                        : new Date());

                payroll.setBankReferenceNo(dto.getBankReferenceNo() != null
                        ? dto.getBankReferenceNo()
                        : "UTR" + System.currentTimeMillis());
            }
        }

        // Optional: update salary or month
        if (dto.getMonth() != null) payroll.setMonth(dto.getMonth());
        if (dto.getGrossSalary() != null) payroll.setGrossSalary(dto.getGrossSalary());
        if (dto.getTotalDeductions() != null) payroll.setTotalDeductions(dto.getTotalDeductions());
        if (dto.getNetSalary() != null) payroll.setNetSalary(dto.getNetSalary());

        payroll.setUpdatedBy("ADMIN");
        payroll.setUpdatedAt(new Date());

        EmployeePayroll updated = payrollRepo.save(payroll);

        return convertToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        EmployeePayroll payroll = payrollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found!"));

        payroll.setIsActive(false); //  Soft delete
        payroll.setUpdatedAt(new Date());
        payroll.setUpdatedBy("ADMIN");

        payrollRepo.save(payroll);
    }


    public EmployeePayrollDTO restore(Long id) {
        EmployeePayroll payroll = payrollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found!"));

        payroll.setIsActive(true);
        payroll.setUpdatedAt(new Date());
        payroll.setUpdatedBy("ADMIN");

        return convertToDTO(payrollRepo.save(payroll));
    }

}
