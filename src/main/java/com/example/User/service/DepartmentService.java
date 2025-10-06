package com.example.User.service;

import com.example.User.dto.DepartmentCreateDto;
import com.example.User.dto.DepartmentResponseDto;
import com.example.User.dto.DepartmentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentCreateDto dto);

    DepartmentResponseDto updateDepartment(Long id, DepartmentUpdateDto dto);

    DepartmentResponseDto getDepartmentById(Long departmentId);

    Page<DepartmentResponseDto> listDepartments(Pageable pageable, String statusFilter);

    void softDeleteDepartment(Long id); // mark INACTIVE
}
