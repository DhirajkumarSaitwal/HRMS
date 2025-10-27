package com.example.User.repository;


import com.example.User.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    boolean existsByNameIgnoreCase(String name);
}
