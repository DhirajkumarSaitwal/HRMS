package com.example.User.serviceimpl;
import com.example.User.entity.KpiMaster;
import com.example.User.repository.DepartmentRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.service.KpiMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KpiMasterServiceImpl implements KpiMasterService {

    @Autowired
    private KpiMasterRepository kpiMasterRepository;

    //  Department Repository for full department fetch
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public KpiMaster createKpi(KpiMaster kpiMaster) {

        // Fetch full Department entity using ID
        Long deptId = kpiMaster.getDepartment().getDepartmentId();
        var department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + deptId));

        //  Check duplicate KPI
        boolean exists = kpiMasterRepository
                .findByDepartment_DepartmentIdAndRoleAndKpiName(
                        deptId,
                        kpiMaster.getRole(),
                        kpiMaster.getKpiName())
                .isPresent();

        if (exists) {
            throw new RuntimeException("KPI already exists for this Department and Role!");
        }

        //  Attach full department object
        kpiMaster.setDepartment(department);
        kpiMaster.setCreatedBy("ADMIN");
        kpiMaster.setUpdatedBy("ADMIN");
        kpiMaster.setIsActive(true);

        return kpiMasterRepository.save(kpiMaster);
    }

    @Override
    public KpiMaster updateKpi(Long id, KpiMaster kpiMaster) {
        KpiMaster existing = kpiMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI not found!"));

        //  Fetch full Department object if provided
        if (kpiMaster.getDepartment() != null && kpiMaster.getDepartment().getDepartmentId() != null) {
            var department = departmentRepository.findById(kpiMaster.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found!"));
            existing.setDepartment(department);
        }

        existing.setKpiName(kpiMaster.getKpiName());
        existing.setKpiDescription(kpiMaster.getKpiDescription());
        existing.setWeightage(kpiMaster.getWeightage());
        existing.setRole(kpiMaster.getRole());
        existing.setUpdatedBy("ADMIN");

        return kpiMasterRepository.save(existing);
    }


    @Override
    public List<KpiMaster> getAllKpis() {
        return kpiMasterRepository.findByIsActiveTrue();
    }

    @Override
    public KpiMaster getKpiById(Long id) {
        return kpiMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI not found!"));
    }

    @Override
    public void softDeleteKpi(Long id) {
        KpiMaster kpi = kpiMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI not found!"));
        kpi.setIsActive(false);
        kpi.setUpdatedBy("ADMIN");
        kpiMasterRepository.save(kpi);
    }

    @Override
    public void hardDeleteKpi(Long id) {
        if (!kpiMasterRepository.existsById(id)) {
            throw new RuntimeException("KPI not found!");
        }
        kpiMasterRepository.deleteById(id);
    }
}
