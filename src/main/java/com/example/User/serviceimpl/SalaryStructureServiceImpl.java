package com.example.User.serviceimpl;

import com.example.User.dto.SalaryStructureDto;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService {

    @Autowired
    private SalaryStructureRepository repo;

    public SalaryStructureDto createStructure(SalaryStructureDto dto) {
        if (repo.existsByStructureName(dto.getStructureName())) {
            throw new RuntimeException("Salary structure name must be unique");
        }
        SalaryStructureMaster s = new SalaryStructureMaster();
        s.setStructureName(dto.getStructureName());
        s.setBasicPay(dto.getBasicPay());
        s.setHra(dto.getHra());
        s.setVariablePay(dto.getVariablePay());
        s.setOtherAllowances(dto.getOtherAllowances());
        s.setDeductions(dto.getDeductions());
        s.setCreatedBy("ADMIN");
        s.setUpdatedBy("ADMIN");
        s = repo.save(s);
        dto.setStructureId(s.getStructureId());
        return dto;
    }

    public List<SalaryStructureDto> getAll() {
        List<SalaryStructureMaster> list = repo.findAll();
        List<SalaryStructureDto> dtos = new ArrayList();
        for (SalaryStructureMaster s : list) {
            SalaryStructureDto dto = new SalaryStructureDto();
            dto.setStructureId(s.getStructureId());
            dto.setStructureName(s.getStructureName());
            dto.setBasicPay(s.getBasicPay());
            dto.setHra(s.getHra());
            dto.setVariablePay(s.getVariablePay());
            dto.setOtherAllowances(s.getOtherAllowances());
            dto.setDeductions(s.getDeductions());
            dto.setCreatedBy("ADMIN");
            dto.setUpdatedBy("ADMIN");
            dtos.add(dto);
        }
        return dtos;
    }

    public SalaryStructureDto getById(Long id) {
        Optional<SalaryStructureMaster> opt = repo.findById(id);
        if (!opt.isPresent()) throw new RuntimeException("Not found");
        SalaryStructureMaster s = opt.get();
        SalaryStructureDto dto = new SalaryStructureDto();
        dto.setStructureId(s.getStructureId());
        dto.setStructureName(s.getStructureName());
        dto.setBasicPay(s.getBasicPay());
        dto.setHra(s.getHra());
        dto.setVariablePay(s.getVariablePay());
        dto.setOtherAllowances(s.getOtherAllowances());
        dto.setDeductions(s.getDeductions());
        dto.setCreatedBy("ADMIN");
        dto.setUpdatedBy("ADMIN");
        return dto;
    }

    public SalaryStructureDto updateStructure(Long id, SalaryStructureDto dto) {
        Optional<SalaryStructureMaster> opt = repo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Structure not found");
        }
        SalaryStructureMaster s = opt.get();
        s.setStructureName(dto.getStructureName());
        s.setBasicPay(dto.getBasicPay());
        s.setHra(dto.getHra());
        s.setVariablePay(dto.getVariablePay());
        s.setOtherAllowances(dto.getOtherAllowances());
        s.setDeductions(dto.getDeductions());
        s.setCreatedBy("ADMIN");
        s.setUpdatedBy("ADMIN");
        repo.save(s);
        dto.setStructureId(s.getStructureId());
        return dto;
    }

    public void deleteStructure(Long id) {
        repo.deleteById(id);
    }





}
