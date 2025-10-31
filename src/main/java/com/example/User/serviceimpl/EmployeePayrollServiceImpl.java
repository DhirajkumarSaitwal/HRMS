package com.example.User.serviceimpl;

import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Override
    public EmployeePayroll createEmployeePayroll(EmployeePayroll payroll) {

        SalaryStructureMaster structure = salaryStructureRepository.findById(
                payroll.getSalaryStructure().getStructureId()
        ).orElseThrow(() -> new RuntimeException("Salary Structure not found"));

        double basic = structure.getBasicPay();
        double hra = structure.getHra();
        double variable = structure.getVariablePay();
        double other = structure.getOtherAllowances();
        double deductions = structure.getDeductions();

        double gross = basic + hra + variable + other;
        double totalDeductions = deductions;
        double net = gross - deductions;

        payroll.setGrossSalary(gross);
        payroll.setTotalDeductions(totalDeductions);
        payroll.setNetSalary(net);
        payroll.setPaymentStatus(EmployeePayroll.PaymentStatus.PENDING);
        payroll.setPaymentDate(LocalDateTime.now()); // ✅ fixed (LocalDateTime)
        payroll.setCreatedAt(LocalDateTime.now());
        payroll.setUpdatedAt(LocalDateTime.now());
        payroll.setCreatedBy("ADMIN");
        payroll.setUpdatedBy("ADMIN");
        payroll.setIsActive(true);

        return employeePayrollRepository.save(payroll);
    }

    @Override
    public EmployeePayroll getEmployeePayrollById(Long id) {
        return employeePayrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));
    }

    @Override
    public List<EmployeePayroll> getAllEmployeePayrolls() {
        return employeePayrollRepository.findByIsActiveTrue();
    }

    @Override
    public EmployeePayroll updateEmployeePayroll(Long id, EmployeePayroll payroll) {
        EmployeePayroll existing = getEmployeePayrollById(id);

        SalaryStructureMaster structure = salaryStructureRepository.findById(
                payroll.getSalaryStructure().getStructureId()
        ).orElseThrow(() -> new RuntimeException("Salary Structure not found"));

        existing.setSalaryStructure(structure);
        existing.setMonth(payroll.getMonth());
        existing.setPaymentDate(payroll.getPaymentDate());
        existing.setBankReferenceNo(payroll.getBankReferenceNo());
        existing.setPaymentStatus(payroll.getPaymentStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setUpdatedBy("ADMIN");

        double basic = structure.getBasicPay();
        double hra = structure.getHra();
        double variable = structure.getVariablePay();
        double other = structure.getOtherAllowances();
        double deductions = structure.getDeductions();

        double gross = basic + hra + variable + other;
        double totalDeductions = deductions;
        double net = gross - deductions;

        existing.setGrossSalary(gross);
        existing.setTotalDeductions(totalDeductions);
        existing.setNetSalary(net);

        return employeePayrollRepository.save(existing);
    }

    @Override
    public void softDeleteEmployeePayroll(Long id) {
        EmployeePayroll payroll = getEmployeePayrollById(id);
        payroll.setIsActive(false);
        payroll.setUpdatedAt(LocalDateTime.now());
        payroll.setUpdatedBy("ADMIN");
        employeePayrollRepository.save(payroll);
    }

    @Override
    public void hardDeleteEmployeePayroll(Long id) {
        employeePayrollRepository.deleteById(id);
    }

    @Override
    public List<EmployeePayroll> getPayrollByEmployeeId(Long employeeId) {
        List<EmployeePayroll> payrolls = employeePayrollRepository.findByEmployee_EmployeeIdAndIsActiveTrue(employeeId);
        if (payrolls.isEmpty()) {
            throw new RuntimeException("No payroll records found for Employee ID: " + employeeId);
        }
        return payrolls;
    }
}
