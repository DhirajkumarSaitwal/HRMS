package com.example.User.repository;

import com.example.User.dto.SalaryStructureDto;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.User.entity.SalaryStructureMaster;

import java.util.List;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructureMaster, Long> {
    boolean existsByStructureName(String name);


}

