package com.example.User.serviceimpl;

import com.example.User.entity.SalaryStructure;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.SalaryStructureService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService {

    @Autowired
    private SalaryStructureRepository repository;

    @Override
    public SalaryStructure createSalaryStructure(SalaryStructure structure) {
        validateSalaryStructure(structure);

        if (repository.existsByStructureNameIgnoreCase(structure.getStructureName())) {
            throw new ValidationException("Salary structure name already exists: " + structure.getStructureName());
        }
        structure.setCreatedBy("Admin");
        return repository.save(structure);
    }

    @Override
    public SalaryStructure updateSalaryStructure(Long id, SalaryStructure updated) {
        SalaryStructure existing = repository.findById(id)
                .orElseThrow(() -> new ValidationException("Salary structure not found with ID: " + id));

        validateSalaryStructure(updated);

        existing.setStructureName(updated.getStructureName());
        existing.setBasicPay(updated.getBasicPay());
        existing.setHra(updated.getHra());
        existing.setVariablePay(updated.getVariablePay());
        existing.setOtherAllowances(updated.getOtherAllowances());
        existing.setDeductions(updated.getDeductions());
        existing.setUpdatedBy("Admin");


        return repository.save(existing);
    }

    @Override
    public List<SalaryStructure> getAllSalaryStructures() {
        return repository.findAll();
    }

    @Override
    public SalaryStructure getSalaryStructureById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("Salary structure not found with ID: " + id));
    }

    @Override
    public void deleteSalaryStructure(Long id) {
        if (!repository.existsById(id)) {
            throw new ValidationException("Salary structure not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    private void validateSalaryStructure(SalaryStructure structure) {
        if (structure.getStructureName() == null || structure.getStructureName().trim().isEmpty()) {
            throw new ValidationException("Structure name cannot be empty");
        }
        if (structure.getBasicPay() == null || structure.getBasicPay() < 0) {
            throw new ValidationException("Basic pay must be a positive number");
        }
        if (structure.getHra() == null || structure.getHra() < 0) {
            throw new ValidationException("HRA must be a positive number");
        }
        if (structure.getVariablePay() == null || structure.getVariablePay() < 0) {
            throw new ValidationException("Variable pay must be a positive number");
        }
        if (structure.getOtherAllowances() == null || structure.getOtherAllowances() < 0) {
            throw new ValidationException("Other allowances must be a positive number");
        }
        if (structure.getDeductions() == null || structure.getDeductions() < 0) {
            throw new ValidationException("Deductions must be a positive number");
        }

        double gross = structure.getBasicPay() + structure.getHra() +
                structure.getVariablePay() + structure.getOtherAllowances() - structure.getDeductions();

        if (gross < 0) {
            throw new ValidationException("Gross salary cannot be negative");
        }
    }
}
