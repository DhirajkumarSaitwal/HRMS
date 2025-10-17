package com.example.User.repository;

import com.example.User.entity.ShiftMaster;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShiftMasterRepository extends JpaRepository<ShiftMaster, Long> {
    boolean existsByShiftNameIgnoreCase(String name);

    boolean existsByShiftNameIgnoreCaseAndShiftIdNot(String shiftName, Long shiftId);
}
