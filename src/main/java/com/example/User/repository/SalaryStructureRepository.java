package com.example.User.repository;

import com.example.User.entity.SalaryStructureMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SalaryStructureRepository extends JpaRepository<SalaryStructureMaster, Long> {
    Optional<SalaryStructureMaster> findByStructureName(String structureName);
}
