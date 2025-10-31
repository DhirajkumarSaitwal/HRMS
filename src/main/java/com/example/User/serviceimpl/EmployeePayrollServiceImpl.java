package com.example.User.serviceimpl;

import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.SalaryStructure;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.EmployeePayrollService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository payrollRepo;

    @Autowired
    private SalaryStructureRepository structureRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public EmployeePayroll processPayroll(EmployeePayroll payroll) {

        if (payrollRepo.existsByEmployeeIdAndMonth(payroll.getEmployeeId(), payroll.getMonth())) {
            throw new ValidationException("Payroll already processed for " + payroll.getMonth());
        }
        boolean employeeExists = employeeRepo.existsById(payroll.getEmployeeId());
        if (!employeeExists) {
            throw new ValidationException("Invalid Employee ID: " + payroll.getEmployeeId());
        }

        if (payroll.getStructureId() == null) {
            throw new ValidationException("Structure ID is required for payroll processing.");
        }

        Long structureIdLong;
        try {
            structureIdLong = Long.parseLong(payroll.getStructureId());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid structure ID format.");
        }

        SalaryStructure structure = structureRepo.findById(structureIdLong)
                .orElseThrow(() -> new ValidationException("Invalid Salary Structure ID: " + payroll.getStructureId()));


        double gross = structure.getBasicPay() + structure.getHra() + structure.getVariablePay() + structure.getOtherAllowances();
        double deductions = structure.getDeductions();
        double net = gross - deductions;

        if (gross < 0 || net < 0) {
            throw new ValidationException("Gross or Net salary cannot be negative.");
        }

        payroll.setGrossSalary(gross);
        payroll.setTotalDeductions(deductions);
        payroll.setNetSalary(net);
        payroll.setPaymentStatus(EmployeePayroll.PaymentStatus.PENDING);

        return payrollRepo.save(payroll);
    }

    @Override
    public EmployeePayroll updatePayroll(Long id, EmployeePayroll updated) {
        EmployeePayroll existing = payrollRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Payroll not found with ID: " + id));

        if (updated.getPaymentStatus() != null)
            existing.setPaymentStatus(updated.getPaymentStatus());
        if (updated.getPaymentDate() != null)
            existing.setPaymentDate(updated.getPaymentDate());
        if (updated.getBankReferenceNo() != null)
            existing.setBankReferenceNo(updated.getBankReferenceNo());

        return payrollRepo.save(existing);
    }

    @Override
    public EmployeePayroll markAsProcessed(Long id, String bankRefNo) {
        EmployeePayroll payroll = payrollRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Payroll not found with ID: " + id));

        payroll.setPaymentStatus(EmployeePayroll.PaymentStatus.PROCESSED);
        payroll.setPaymentDate(LocalDate.now());
        payroll.setBankReferenceNo(bankRefNo);

        return payrollRepo.save(payroll);
    }

    @Override
    public List<EmployeePayroll> getAllPayrolls() {
        return payrollRepo.findAll();
    }

    @Override
    public List<EmployeePayroll> getPayrollsByEmployee(Long employeeId) {
        return payrollRepo.findByEmployeeId(employeeId);
    }

    @Override
    public EmployeePayroll getPayrollById(Long id) {
        return payrollRepo.findById(id)
                .orElseThrow(() -> new ValidationException("Payroll not found with ID: " + id));
    }

    @Override
    public void deletePayroll(Long id) {
        if (!payrollRepo.existsById(id)) {
            throw new ValidationException("Payroll not found with ID: " + id);
        }
        payrollRepo.deleteById(id);
    }
}
