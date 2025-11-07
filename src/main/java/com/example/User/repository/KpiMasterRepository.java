package com.example.User.repository;
import com.example.User.entity.KpiMaster;
import com.example.User.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface KpiMasterRepository extends JpaRepository<KpiMaster, Long> {

    Optional<KpiMaster> findByDepartment_DepartmentIdAndRoleAndKpiName(Long departmentId, Role role, String kpiName);

    List<KpiMaster> findByIsActiveTrue();

    //  Native query to fetch KPI with department details
    @Query(value = """
        SELECT 
            k.kpi_id AS kpiId,
            k.kpi_name AS kpiName,
            k.kpi_description AS kpiDescription,
            k.weightage AS weightage,
            k.role AS role,
            k.is_active AS isActive,
            d.department_id AS departmentId,
            d.name AS departmentName,
            d.code AS departmentCode,
            d.description AS departmentDescription
        FROM kpi_master k
        JOIN department d ON k.department_id = d.department_id
        WHERE k.is_active = true
        """, nativeQuery = true)
    List<Map<String, Object>> fetchAllKpiWithDepartment();
}
