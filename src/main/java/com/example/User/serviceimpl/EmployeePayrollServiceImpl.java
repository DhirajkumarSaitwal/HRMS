package com.example.User.serviceimpl;

import com.example.User.dto.EmployeePayrollRequestDTO;
import com.example.User.dto.EmployeePayrollResponseDTO;
import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.PaymentStatus;
import com.example.User.entity.SalaryStructure;
import com.example.User.exception.ResourceAlreadyExistsException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Override
    public EmployeePayrollResponseDTO createPayroll(EmployeePayrollRequestDTO requestDTO) {
        Optional<EmployeePayroll> existingPayroll = employeePayrollRepository
                .findByEmployeeIdAndMonth(requestDTO.getEmployeeId(), requestDTO.getMonth());

        if (existingPayroll.isPresent()) {
            throw new ResourceAlreadyExistsException("Payroll already exists for this employee and month!");
        }


        Optional<SalaryStructure> optionalStructure = salaryStructureRepository.findById(requestDTO.getStructureId());
        SalaryStructure structure;
        if (optionalStructure.isPresent()) {
            structure = optionalStructure.get();
        } else {
            throw new ResourceNotFoundException("Salary Structure not found!");
        }

        double grossSalary = structure.getBasicPay()
                + structure.getHra()
                + structure.getVariablePay()
                + structure.getOtherAllowances();

        double totalDeductions = structure.getDeductions();
        double netSalary = grossSalary - totalDeductions;


        if (requestDTO.getPaymentDate() != null &&
                requestDTO.getPaymentDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Payment date cannot be in the future!");
        }


        EmployeePayroll payroll = new EmployeePayroll();
        payroll.setEmployeeId(requestDTO.getEmployeeId());
        payroll.setMonth(requestDTO.getMonth());
        payroll.setStructureId(requestDTO.getStructureId());
        payroll.setGrossSalary(BigDecimal.valueOf(grossSalary));
        payroll.setTotalDeductions(BigDecimal.valueOf(totalDeductions));
        payroll.setNetSalary(BigDecimal.valueOf(netSalary));
        payroll.setPaymentStatus(PaymentStatus.valueOf(requestDTO.getPaymentStatus()));
        payroll.setPaymentDate(requestDTO.getPaymentDate());
        payroll.setBankReferenceNo(requestDTO.getBankReferenceNo());
        payroll.setCreatedBy("ADMIN");
        payroll.setUpdatedBy("ADMIN");

        employeePayrollRepository.save(payroll);


        EmployeePayrollResponseDTO response = new EmployeePayrollResponseDTO();
        response.setPayrollId(payroll.getPayrollId());
        response.setEmployeeId(payroll.getEmployeeId());
        response.setMonth(payroll.getMonth());
        response.setGrossSalary(payroll.getGrossSalary());
        response.setTotalDeductions(payroll.getTotalDeductions());
        response.setNetSalary(payroll.getNetSalary());
        response.setPaymentStatus(payroll.getPaymentStatus());
        response.setPaymentDate(payroll.getPaymentDate());
        response.setBankReferenceNo(payroll.getBankReferenceNo());
        response.setCreatedAt(payroll.getCreatedAt());
        response.setUpdatedAt(payroll.getUpdatedAt());
        response.setCreatedBy(payroll.getCreatedBy());
        response.setUpdatedBy(payroll.getUpdatedBy());

        return response;
    }

    @Override
    public EmployeePayrollResponseDTO getPayrollById(Long payrollId) {
        EmployeePayroll payroll = employeePayrollRepository.findById(payrollId).orElse(null);

        if (payroll == null) {
            throw new ResourceNotFoundException("Payroll not found with ID: " + payrollId);
        }


        EmployeePayrollResponseDTO dto = new EmployeePayrollResponseDTO();
        dto.setPayrollId(payroll.getPayrollId());
        dto.setEmployeeId(payroll.getEmployeeId());
        dto.setMonth(payroll.getMonth());
        dto.setStructureId(payroll.getStructureId());
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
    public EmployeePayroll processPayroll(EmployeePayroll payroll) {
        double gross = payroll.getGrossSalary() != null ? payroll.getGrossSalary().doubleValue() : 0.0;
        double deductions = payroll.getTotalDeductions() != null ? payroll.getTotalDeductions().doubleValue() : 0.0;
        double net = gross - deductions;

        payroll.setNetSalary(BigDecimal.valueOf(net));
        payroll.setPaymentStatus(PaymentStatus.PROCESSED);
        payroll.setPaymentDate(LocalDate.now());

        return employeePayrollRepository.save(payroll);
    }

    @Override
    public List<EmployeePayrollResponseDTO> getAllPayrolls() {
        List<EmployeePayroll> payrolls = employeePayrollRepository.findAll();
        List<EmployeePayrollResponseDTO> responses = new ArrayList<EmployeePayrollResponseDTO>();

        for (EmployeePayroll p : payrolls) {
            EmployeePayrollResponseDTO dto = new EmployeePayrollResponseDTO();
            dto.setPayrollId(p.getPayrollId());
            dto.setEmployeeId(p.getEmployeeId());
            dto.setMonth(p.getMonth());
            dto.setStructureId(p.getStructureId());
            dto.setGrossSalary(p.getGrossSalary());
            dto.setTotalDeductions(p.getTotalDeductions());
            dto.setNetSalary(p.getNetSalary());
            dto.setPaymentStatus(p.getPaymentStatus());
            dto.setPaymentDate(p.getPaymentDate());
            dto.setBankReferenceNo(p.getBankReferenceNo());
            dto.setCreatedBy(p.getCreatedBy());
            dto.setUpdatedBy(p.getUpdatedBy());
            dto.setCreatedAt(p.getCreatedAt());
            dto.setUpdatedAt(p.getUpdatedAt());
            responses.add(dto);
        }
        return responses;
    }

    @Override
    public void deletePayroll(Long payrollId) {
        EmployeePayroll payroll = employeePayrollRepository.findById(payrollId).orElse(null);
        if (payroll == null) {
            throw new ResourceNotFoundException("Payroll not found with ID: " + payrollId);
        }
        employeePayrollRepository.delete(payroll);
    }

    @Override
    public EmployeePayrollResponseDTO updatePayroll(Long id, EmployeePayrollRequestDTO payrollRequest) {
        EmployeePayroll existing = employeePayrollRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("Payroll not found with ID: " + id);
        }


        existing.setGrossSalary(BigDecimal.valueOf(payrollRequest.getGrossSalary()));
        existing.setTotalDeductions(BigDecimal.valueOf(payrollRequest.getTotalDeductions()));
        existing.setNetSalary(BigDecimal.valueOf(payrollRequest.getNetSalary()));
        existing.setPaymentStatus(PaymentStatus.valueOf(payrollRequest.getPaymentStatus().toUpperCase()));


        if (payrollRequest.getPaymentDate() != null) {
            existing.setPaymentDate(payrollRequest.getPaymentDate());
        }

        existing.setBankReferenceNo(payrollRequest.getBankReferenceNo());
        existing.setUpdatedBy("HR_Manager");
        existing.setUpdatedAt(LocalDateTime.now());

        employeePayrollRepository.save(existing);


        EmployeePayrollResponseDTO response = new EmployeePayrollResponseDTO();
        response.setPayrollId(existing.getPayrollId());
        response.setEmployeeId(existing.getEmployeeId());
        response.setMonth(existing.getMonth());
        response.setStructureId(existing.getStructureId());
        response.setGrossSalary(existing.getGrossSalary());
        response.setTotalDeductions(existing.getTotalDeductions());
        response.setNetSalary(existing.getNetSalary());
        response.setPaymentStatus(existing.getPaymentStatus());
        response.setPaymentDate(existing.getPaymentDate());
        response.setBankReferenceNo(existing.getBankReferenceNo());
        response.setCreatedBy(existing.getCreatedBy());
        response.setUpdatedBy(existing.getUpdatedBy());
        response.setCreatedAt(existing.getCreatedAt());
        response.setUpdatedAt(existing.getUpdatedAt());


        return response;
    }

    @Override
    public List<EmployeePayroll> getPayrollsByEmployeeId(Long employeeId) {
        List<EmployeePayroll> all = employeePayrollRepository.findAll();
        List<EmployeePayroll> result = new ArrayList<EmployeePayroll>();
        for (EmployeePayroll p : all) {
            if (p.getEmployeeId().equals(employeeId)) {
                result.add(p);
            }
        }
        return result;
    }
}


