package com.example.User.repository;

import com.example.User.entity.SalaryStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryStructureRepository extends JpaRepository<SalaryStructure, Long> {
    boolean existsByStructureName(String structureName);
    Optional<SalaryStructure> findByStructureName(String structureName);
}

