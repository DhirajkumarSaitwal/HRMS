package com.example.User.service;

import com.example.User.dto.SalaryStructureDto;
import java.util.List;

public interface SalaryStructureService {
    SalaryStructureDto createStructure(SalaryStructureDto dto);
    SalaryStructureDto getById(Long id);
    List<SalaryStructureDto> getAll();
    SalaryStructureDto updateStructure(Long id, SalaryStructureDto dto);
    void deleteStructure(Long id);

}

