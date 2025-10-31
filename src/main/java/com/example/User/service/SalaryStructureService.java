package com.example.User.service;

import com.example.User.entity.SalaryStructure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryStructureService {

    SalaryStructure createSalaryStructure(SalaryStructure structure);

    SalaryStructure updateSalaryStructure(Long id, SalaryStructure structure);

    List<SalaryStructure> getAllSalaryStructures();

    SalaryStructure getSalaryStructureById(Long id);

    void deleteSalaryStructure(Long id);
}
