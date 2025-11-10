package com.example.User.repository;

import com.example.User.entity.KpiMaster;
import com.example.User.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KpiMasterRepository extends JpaRepository<KpiMaster, Long> {
        Optional<KpiMaster> findByDepartment_DepartmentIdAndRoleAndKpiName(Long departmentId, Role role, String kpiName);
}
