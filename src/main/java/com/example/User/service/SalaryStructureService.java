package com.example.User.service;

import com.example.User.dto.SalaryStructureDTO;
import com.example.User.entity.SalaryStructureMaster;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface SalaryStructureService {
    SalaryStructureDTO createSalaryStructure(SalaryStructureDTO dto);
    List<SalaryStructureMaster> getAllStructures();
    SalaryStructureDTO getById(Long id);
    SalaryStructureDTO updateSalaryStructure(Long id, SalaryStructureDTO dto);
    void deleteSalaryStructure(Long structureId);

    // Soft delete method
    void softDeleteSalaryStructure(Long structureId);
}
