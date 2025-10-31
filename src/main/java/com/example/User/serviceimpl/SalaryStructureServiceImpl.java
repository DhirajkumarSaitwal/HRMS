package com.example.User.serviceimpl;

import com.example.User.dto.SalaryStructureRequestDTO;
import com.example.User.dto.SalaryStructureResponseDTO;
import com.example.User.entity.SalaryStructure;
import com.example.User.exception.ResourceAlreadyExistsException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService {

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;


    @Override
    public SalaryStructureResponseDTO createSalaryStructure(SalaryStructureRequestDTO requestDto) {
        SalaryStructure existing = salaryStructureRepository.findByStructureName(requestDto.getStructureName());
        if (existing != null) {
            throw new ResourceAlreadyExistsException("Salary Structure name already exists: " + requestDto.getStructureName());
        }


        if (requestDto.getBasicPay() < 0 ||
                requestDto.getHra() < 0 ||
                requestDto.getVariablePay() < 0 ||
                requestDto.getOtherAllowances() < 0 ||
                requestDto.getDeductions() < 0) {
            throw new IllegalArgumentException("Salary components cannot be negative");
        }


        double gross = requestDto.getBasicPay() + requestDto.getHra()
                + requestDto.getVariablePay() + requestDto.getOtherAllowances()
                - requestDto.getDeductions();


        SalaryStructure entity = new SalaryStructure();
        entity.setStructureName(requestDto.getStructureName());
        entity.setBasicPay(requestDto.getBasicPay());
        entity.setHra(requestDto.getHra());
        entity.setVariablePay(requestDto.getVariablePay());
        entity.setOtherAllowances(requestDto.getOtherAllowances());
        entity.setDeductions(requestDto.getDeductions());
        entity.setCreatedBy("ADMIN");
        entity.setUpdatedBy("ADMIN");
        entity.setCreatedAt(LocalDateTime.now());


        salaryStructureRepository.save(entity);


        SalaryStructureResponseDTO responseDto = new SalaryStructureResponseDTO();
        responseDto.setStructureId(entity.getStructureId());
        responseDto.setStructureName(entity.getStructureName());
        responseDto.setBasicPay(BigDecimal.valueOf(entity.getBasicPay()));
        responseDto.setHra(BigDecimal.valueOf(entity.getHra()));
        responseDto.setVariablePay(BigDecimal.valueOf(entity.getVariablePay()));
        responseDto.setOtherAllowances(BigDecimal.valueOf(entity.getOtherAllowances()));
        responseDto.setDeductions(BigDecimal.valueOf(entity.getDeductions()));
        responseDto.setGrossSalary(BigDecimal.valueOf(gross));
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setUpdatedAt(LocalDateTime.now());
        responseDto.setCreatedBy(entity.getCreatedBy());
        responseDto.setUpdatedBy(entity.getUpdatedBy());

        return responseDto;
    }

    @Override
    public SalaryStructureResponseDTO updateSalaryStructure(Long id, SalaryStructureRequestDTO requestDto) {
        SalaryStructure existing = null;
        if (salaryStructureRepository.findById(id).isPresent()) {
            existing = salaryStructureRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("Salary structure not found with id: " + id);
        }


        existing.setStructureName(requestDto.getStructureName());
        existing.setBasicPay(requestDto.getBasicPay());
        existing.setHra(requestDto.getHra());
        existing.setVariablePay(requestDto.getVariablePay());
        existing.setOtherAllowances(requestDto.getOtherAllowances());
        existing.setDeductions(requestDto.getDeductions());
        existing.setUpdatedBy("ADMIN");

        SalaryStructure updated = salaryStructureRepository.save(existing);

        double grossSalary = updated.getBasicPay() + updated.getHra()
                + updated.getVariablePay() + updated.getOtherAllowances() - updated.getDeductions();

        SalaryStructureResponseDTO response = new SalaryStructureResponseDTO();
        response.setStructureId(updated.getStructureId());
        response.setStructureName(updated.getStructureName());
        response.setBasicPay(BigDecimal.valueOf(updated.getBasicPay()));
        response.setHra(BigDecimal.valueOf(updated.getHra()));
        response.setVariablePay(BigDecimal.valueOf(updated.getVariablePay()));
        response.setOtherAllowances(BigDecimal.valueOf(updated.getOtherAllowances()));
        response.setDeductions(BigDecimal.valueOf(updated.getDeductions()));
        response.setGrossSalary(BigDecimal.valueOf(grossSalary));
        response.setCreatedAt(updated.getCreatedAt());
        response.setUpdatedAt(updated.getCreatedAt());
        response.setCreatedBy(updated.getCreatedBy());
        response.setUpdatedBy(updated.getUpdatedBy());

        return response;
    }

    @Override
    public List<SalaryStructure> getAllSalaryStructures() {
        return salaryStructureRepository.findAll();
    }

    @Override
    public SalaryStructure getSalaryStructureById(Long id) {
        if (salaryStructureRepository.findById(id).isPresent()) {
            return salaryStructureRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("Salary Structure not found for ID: " + id);
        }
    }

    @Override
    public void deleteSalaryStructure(Long id) {
        SalaryStructure structure = null;
        if (salaryStructureRepository.findById(id).isPresent()) {
            structure = salaryStructureRepository.findById(id).get();
        } else {
            throw new ResourceNotFoundException("Salary Structure not found for ID: " + id);
        }
        salaryStructureRepository.delete(structure);
    }
}
