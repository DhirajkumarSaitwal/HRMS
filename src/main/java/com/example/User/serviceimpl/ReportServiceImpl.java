package com.example.User.serviceimpl;

import com.example.User.dto.EmployeeDTO;
import com.example.User.entity.Employee;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.ReportService;
import com.example.User.util.ReportExportUtil;
import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private  EmployeeRepository employeeRepository;

    @Value("${report.export.path:./exports}")
    private String exportBasePath;

    public ReportServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public String generateEmployeeReport(String reportType, String exportType) throws Exception {
        Assert.hasText(reportType, "reportType must be provided");
        Assert.hasText(exportType, "exportType must be provided");

        if (!"EmployeeMaster".equalsIgnoreCase(reportType)) {
            throw new IllegalArgumentException("Unsupported reportType. Only EmployeeMaster is allowed.");
        }

        String ext;
        if ("excel".equalsIgnoreCase(exportType)) {
            ext = "xlsx";
        } else if ("csv".equalsIgnoreCase(exportType)) {
            ext = "csv";
        } else {
            throw new IllegalArgumentException("exportType must be 'excel' or 'csv'");
        }

        // fetch data
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDTO> dtos = employees.stream().map(e -> EmployeeDTO.builder()
                .employeeId(e.getEmployeeId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .status(e.getStatus())
                .department(e.getDepartment())
                .email(e.getEmail())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build()
        ).collect(Collectors.toList());

        // filename
        String fileName = String.format("Employee_Master_Report_%d.%s", System.currentTimeMillis(), ext);
        Path exportDir = Paths.get(exportBasePath).toAbsolutePath().normalize();
        Path filePath = exportDir.resolve(fileName);

        // generate file
        if ("xlsx".equalsIgnoreCase(ext)) {
            ReportExportUtil.exportToExcel(dtos, filePath);
        } else {
            ReportExportUtil.exportToCsv(dtos, filePath);
        }

        return filePath.toString();
    }
    }
