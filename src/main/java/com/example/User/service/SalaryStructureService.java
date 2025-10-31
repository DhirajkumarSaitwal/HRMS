package com.example.User.service;

import com.example.User.dto.SalaryStructureRequestDTO;
import com.example.User.dto.SalaryStructureResponseDTO;
import com.example.User.entity.SalaryStructure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryStructureService {
    SalaryStructureResponseDTO createSalaryStructure(SalaryStructureRequestDTO requestDto);

    SalaryStructureResponseDTO updateSalaryStructure(Long id, SalaryStructureRequestDTO requestDto);

    List<SalaryStructure> getAllSalaryStructures();

    SalaryStructure getSalaryStructureById(Long id);

    void deleteSalaryStructure(Long id);

}
