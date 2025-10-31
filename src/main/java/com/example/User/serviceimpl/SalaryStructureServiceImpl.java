package com.example.User.serviceimpl;
import com.example.User.dto.SalaryStructureDTO;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService {

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Override
    public SalaryStructureDTO createSalaryStructure(SalaryStructureDTO dto) {
        if (salaryStructureRepository.findByStructureName(dto.getStructureName()).isPresent()) {
            throw new RuntimeException("Salary Structure name must be unique");
        }

        SalaryStructureMaster entity = mapToEntity(dto);
        entity.setCreatedBy("ADMIN");
        entity.setUpdatedBy("ADMIN");
        entity.setIsActive(true);

        SalaryStructureMaster saved = salaryStructureRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<SalaryStructureMaster> getAllStructures() {
        return salaryStructureRepository.findAll();
    }

    @Override
    public SalaryStructureDTO getById(Long id) {
        SalaryStructureMaster entity = salaryStructureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary Structure not found with ID: " + id));
        return mapToDTO(entity);
    }

    @Override
    public SalaryStructureDTO updateSalaryStructure(Long id, SalaryStructureDTO dto) {
        SalaryStructureMaster entity = salaryStructureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary Structure not found with ID: " + id));

        entity.setStructureName(dto.getStructureName());
        entity.setBasicPay(dto.getBasicPay());
        entity.setHra(dto.getHra());
        entity.setVariablePay(dto.getVariablePay());
        entity.setOtherAllowances(dto.getOtherAllowances());
        entity.setDeductions(dto.getDeductions());
        entity.setUpdatedBy("ADMIN");

        SalaryStructureMaster updated = salaryStructureRepository.save(entity);
        return mapToDTO(updated);
    }

    // Hard delete - record permanently remove
    @Override
    public void deleteSalaryStructure(Long structureId) {
        Optional<SalaryStructureMaster> existing = salaryStructureRepository.findById(structureId);
        if (existing.isPresent()) {
            salaryStructureRepository.deleteById(structureId);
        } else {
            throw new RuntimeException("Salary Structure not found with ID: " + structureId);
        }
    }

    // Soft delete - (Inactive)
    @Override
    public void softDeleteSalaryStructure(Long structureId) {
        SalaryStructureMaster entity = salaryStructureRepository.findById(structureId)
                .orElseThrow(() -> new RuntimeException("Salary Structure not found with ID: " + structureId));

        entity.setIsActive(false);
        entity.setUpdatedBy("ADMIN");
        salaryStructureRepository.save(entity);
    }

    private SalaryStructureMaster mapToEntity(SalaryStructureDTO dto) {
        SalaryStructureMaster entity = new SalaryStructureMaster();
        entity.setStructureName(dto.getStructureName());
        entity.setBasicPay(dto.getBasicPay());
        entity.setHra(dto.getHra());
        entity.setVariablePay(dto.getVariablePay());
        entity.setOtherAllowances(dto.getOtherAllowances());
        entity.setDeductions(dto.getDeductions());
        return entity;
    }

    private SalaryStructureDTO mapToDTO(SalaryStructureMaster entity) {
        SalaryStructureDTO dto = new SalaryStructureDTO();
        dto.setStructureId(entity.getStructureId());
        dto.setStructureName(entity.getStructureName());
        dto.setBasicPay(entity.getBasicPay());
        dto.setHra(entity.getHra());
        dto.setVariablePay(entity.getVariablePay());
        dto.setOtherAllowances(entity.getOtherAllowances());
        dto.setDeductions(entity.getDeductions());
        return dto;
    }
}
