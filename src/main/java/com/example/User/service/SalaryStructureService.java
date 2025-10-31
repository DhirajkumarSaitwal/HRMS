package com.example.User.service;

import com.example.User.dto.CreateSalaryStructureRequest;
import com.example.User.dto.SalaryStructureDto;

import java.util.List;

public interface SalaryStructureService {
    SalaryStructureDto createSalaryStructure(CreateSalaryStructureRequest request);
    List<SalaryStructureDto> getAllSalaryStructures();
    SalaryStructureDto getSalaryStructureById(Long id);
    SalaryStructureDto updateSalaryStructure(Long id, CreateSalaryStructureRequest request);
    String deleteSalaryStructure(Long id);

}
