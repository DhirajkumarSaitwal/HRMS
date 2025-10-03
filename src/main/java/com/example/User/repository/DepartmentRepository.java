package com.example.User.repository;

import com.example.User.entity.Department;
import com.example.User.entity.DepartmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameIgnoreCase(String name);

    Optional<Department> findByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    Page<Department> findAllByStatus(DepartmentStatus status, Pageable pageable);

}
