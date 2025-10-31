package com.example.User.repository;

import com.example.User.entity.SalaryStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, Long> {

    SalaryStructure findByStructureName(String structureName);
}
