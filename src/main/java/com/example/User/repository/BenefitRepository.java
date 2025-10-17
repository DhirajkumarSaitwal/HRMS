package com.example.User.repository;


import com.example.User.entity.BenefitMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BenefitRepository extends JpaRepository<BenefitMaster, Long> {
    Optional<BenefitMaster> findByBenefitName(String name);
}
