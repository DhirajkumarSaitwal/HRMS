package com.example.User.serviceimpl;

import com.example.User.dto.EmployeePayrollDTO;
import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.PaymentStatus;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Override
    public EmployeePayrollDTO createPayroll(EmployeePayrollDTO dto) {
        // Validate Employee and Structure exist
        if (!employeeRepository.existsById(dto.getEmployeeId())) {
            throw new RuntimeException("Employee not found with ID: " + dto.getEmployeeId());
        }
        if (!salaryStructureRepository.existsById(dto.getStructureId())) {
            throw new RuntimeException("Salary Structure not found with ID: " + dto.getStructureId());
        }

        EmployeePayroll payroll = EmployeePayroll.builder()
                .employeeId(dto.getEmployeeId())
                .month(dto.getMonth())
                .structureId(dto.getStructureId())
                .grossSalary(dto.getGrossSalary())
                .totalDeductions(dto.getTotalDeductions())
                .netSalary(dto.getNetSalary())
                .paymentStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : PaymentStatus.PENDING)
                .paymentDate(dto.getPaymentDate())
                .bankReferenceNo(dto.getBankReferenceNo())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        EmployeePayroll saved = repository.save(payroll);

        return EmployeePayrollDTO.builder()
                .payrollId(saved.getPayrollId())
                .employeeId(saved.getEmployeeId())
                .month(saved.getMonth())
                .structureId(saved.getStructureId())
                .grossSalary(saved.getGrossSalary())
                .totalDeductions(saved.getTotalDeductions())
                .netSalary(saved.getNetSalary())
                .paymentStatus(saved.getPaymentStatus())
                .paymentDate(saved.getPaymentDate())
                .bankReferenceNo(saved.getBankReferenceNo())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    @Override
    public List<EmployeePayroll> getAllPayrolls() {
        return repository.findAll();
    }

    @Override
    public EmployeePayrollDTO getPayrollById(Long id) {
        EmployeePayroll payroll = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found for ID: " + id));

        return EmployeePayrollDTO.builder()
                .payrollId(payroll.getPayrollId())
                .employeeId(payroll.getEmployeeId())
                .month(payroll.getMonth())
                .structureId(payroll.getStructureId())
                .grossSalary(payroll.getGrossSalary())
                .totalDeductions(payroll.getTotalDeductions())
                .netSalary(payroll.getNetSalary())
                .paymentStatus(payroll.getPaymentStatus())
                .paymentDate(payroll.getPaymentDate())
                .bankReferenceNo(payroll.getBankReferenceNo())
                .createdAt(payroll.getCreatedAt())
                .updatedAt(payroll.getUpdatedAt())
                .build();
    }

    @Override
    public EmployeePayrollDTO updatePayroll(Long id, EmployeePayrollDTO dto) {
        EmployeePayroll existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found for ID: " + id));

        // Update fields
        existing.setEmployeeId(dto.getEmployeeId());
        existing.setMonth(dto.getMonth());
        existing.setStructureId(dto.getStructureId());
        existing.setGrossSalary(dto.getGrossSalary());
        existing.setTotalDeductions(dto.getTotalDeductions());
        existing.setNetSalary(dto.getNetSalary());
        existing.setPaymentStatus(dto.getPaymentStatus());
        existing.setPaymentDate(dto.getPaymentDate());
        existing.setBankReferenceNo(dto.getBankReferenceNo());
        existing.setUpdatedAt(LocalDateTime.now());

        repository.save(existing);
        return mapToDTO(existing);
    }

    // Helper method
    private EmployeePayrollDTO mapToDTO(EmployeePayroll payroll) {
        return EmployeePayrollDTO.builder()
                .payrollId(payroll.getPayrollId())
                .employeeId(payroll.getEmployeeId())
                .month(payroll.getMonth())
                .structureId(payroll.getStructureId())
                .grossSalary(payroll.getGrossSalary())
                .totalDeductions(payroll.getTotalDeductions())
                .netSalary(payroll.getNetSalary())
                .paymentStatus(payroll.getPaymentStatus())
                .paymentDate(payroll.getPaymentDate())
                .bankReferenceNo(payroll.getBankReferenceNo())
                .createdAt(payroll.getCreatedAt())
                .updatedAt(payroll.getUpdatedAt())
                .build();
    }

    @Override
    public void deletePayroll(Long id) {
        EmployeePayroll existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll record not found for ID: " + id));
        repository.delete(existing);
    }
}


