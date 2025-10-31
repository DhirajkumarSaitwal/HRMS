package com.example.User.serviceimpl;

import com.example.User.dto.CreateSalaryStructureRequest;
import com.example.User.dto.SalaryStructureDto;
import com.example.User.entity.SalaryStructure;
import com.example.User.exception.DuplicateResourceException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.SalaryStructureService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalaryStructureServiceImpl implements SalaryStructureService {

    private final SalaryStructureRepository repository;

    public SalaryStructureServiceImpl(SalaryStructureRepository repository) {
        this.repository = repository;
    }

    @Override
    public SalaryStructureDto createSalaryStructure(CreateSalaryStructureRequest request) {
        validateCreateRequest(request);
        if (repository.existsByStructureName(request.getStructureName())) {
            throw new DuplicateResourceException("Salary structure with name already exists: " + request.getStructureName());
        }
        SalaryStructure entity = SalaryStructure.builder()
                .structureName(request.getStructureName().trim())
                .basicPay(defaultZero(request.getBasicPay()))
                .hra(defaultZero(request.getHra()))
                .variablePay(defaultZero(request.getVariablePay()))
                .otherAllowances(defaultZero(request.getOtherAllowances()))
                .deductions(defaultZero(request.getDeductions()))
//                .createdBy(request.getCreatedBy())
//                .updatedBy(request.getUpdatedBy())
                .createdBy("Admin")
                .updatedBy("Admin")
                .build();
        BigDecimal gross = calcGross(entity);
        if (gross.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Calculated gross salary is negative. Check amounts.");
        }
        SalaryStructure saved = repository.save(entity);
        return toDto(saved);
    }

    private void validateCreateRequest(CreateSalaryStructureRequest req) {
        if (req.getBasicPay() == null || req.getHra() == null) {
            throw new ValidationException("basicPay and hra must not be null");
        }
        if (req.getBasicPay().compareTo(BigDecimal.ZERO) < 0 ||
                req.getHra().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("basicPay and hra must be non-negative");
        }
    }

    private BigDecimal defaultZero(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val;
    }

    private BigDecimal calcGross(SalaryStructure s) {
        return defaultZero(s.getBasicPay())
                .add(defaultZero(s.getHra()))
                .add(defaultZero(s.getVariablePay()))
                .add(defaultZero(s.getOtherAllowances()))
                .subtract(defaultZero(s.getDeductions()));
    }
    // Helper / mapping
    private SalaryStructureDto toDto(SalaryStructure s) {
        return SalaryStructureDto.builder()
                .structureId(s.getStructureId())
                .structureName(s.getStructureName())
                .basicPay(s.getBasicPay())
                .hra(s.getHra())
                .variablePay(s.getVariablePay())
                .otherAllowances(s.getOtherAllowances())
                .deductions(s.getDeductions())
                .createdBy("Admin")
                .updatedBy("Admin")
                .build();
    }



    @Override
    public List<SalaryStructureDto> getAllSalaryStructures() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public SalaryStructureDto getSalaryStructureById(Long id) {
        SalaryStructure s = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalaryStructure not found: " + id));
        return toDto(s);
    }


    @Override
    public SalaryStructureDto updateSalaryStructure(Long id, CreateSalaryStructureRequest request) {
        SalaryStructure existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalaryStructure not found: " + id));

        if (!existing.getStructureName().equals(request.getStructureName())
                && repository.existsByStructureName(request.getStructureName())) {
            throw new DuplicateResourceException("Salary structure with name already exists: " + request.getStructureName());
        }
        existing.setStructureName(request.getStructureName().trim());
        existing.setBasicPay(defaultZero(request.getBasicPay()));
        existing.setHra(defaultZero(request.getHra()));
        existing.setVariablePay(defaultZero(request.getVariablePay()));
        existing.setOtherAllowances(defaultZero(request.getOtherAllowances()));
        existing.setDeductions(defaultZero(request.getDeductions()));
        existing.setUpdatedBy(request.getUpdatedBy());

        BigDecimal gross = calcGross(existing);
        if (gross.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Calculated gross salary is negative. Check amounts.");
        }
        SalaryStructure saved = repository.save(existing);
        return toDto(saved);
    }

//    @Override
//    public void deleteSalaryStructure(Long id) {
//        SalaryStructure existing = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("SalaryStructure not found: " + id));
//        repository.delete(existing);
//    }

    @Override
    public String deleteSalaryStructure(Long id) {
        SalaryStructure existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salary Structure not found: " + id));
        repository.delete(existing);
        return "Salary Structure deleted successfully";
    }









}




