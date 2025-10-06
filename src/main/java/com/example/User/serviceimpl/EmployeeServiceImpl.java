package com.example.User.serviceimpl;


import com.example.User.entity.Employee;
import com.example.User.entity.EmploymentType;
import com.example.User.entity.User;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//created by hamad task2
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    //@Autowired
    //private EmploymentType employmentType

//    @Override
//    public Employee createEmployee(Employee employee) {
//        return employeeRepository.save(employee);
//    }
@Override
public Employee createEmployee(Employee employee) {
    if (employee.getReportingManager() != null && employee.getReportingManager().getId() != null) {
        User manager = userRepository.findById(employee.getReportingManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + employee.getReportingManager().getId()));
        employee.setReportingManager(manager);
    }

    if (employee.getHrbp() != null && employee.getHrbp().getId() != null) {
        User hrbp = userRepository.findById(employee.getHrbp().getId())
                .orElseThrow(() -> new RuntimeException("HRBP not found with ID: " + employee.getHrbp().getId()));
        employee.setHrbp(hrbp);
    }

    return employeeRepository.save(employee);
}


    @Override
    public Employee updateEmployee(Long employeeId, Employee employee) {
        Optional<Employee> existing = employeeRepository.findById(employeeId);
        if (existing.isPresent()) {
            employee.setEmployeeId(employeeId);
            return employeeRepository.save(employee);
        }
        return null; // can throw custom exception
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    //added becouse need all employee related manager
    @Override
    public List<Employee> getTeamMembers(Long managerId) {
        return employeeRepository.findByReportingManagerId(managerId);
    }

//    @Override
//    public Long getLoggedInEmployeeId(Authentication authentication) {
//
//            String email = null;
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                email = ((UserDetails) principal).getUsername();
//            } else if (principal instanceof String) {
//                email = (String) principal;
//            }
//
//            Employee employee = employeeRepository.findByEmail(email);
//            return employee.getId();
//
//    }

    //    @Override
//    public Long getLoggedInEmployeeId(Authentication authentication) {
//        // Example if you store email as username
//        String email = authentication.name();
//        Employee employee = employeeRepository.findByEmail(email);
//        return employee != null ? employee.getEmployeeId() : null;
//    }

    //-------------

    @Override
    public List<Employee> searchEmployees(String name, String email, String department, String designation) {
        List<Employee> result = employeeRepository.findAll();

        if (name != null && !name.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name))
                    .collect(Collectors.toList());
        }
        if (email != null && !email.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getEmail().contains(email))
                    .collect(Collectors.toList());
        }
        if (department != null && !department.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        }
        if (designation != null && !designation.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDesignation().equalsIgnoreCase(designation))
                    .collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<Employee> filterEmployees(String status, String department, String employmentType) {
        List<Employee> result = employeeRepository.findAll();

        if (status != null && !status.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }
        if (department != null && !department.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        }
//        if (employmentType != null && !employmentType.isEmpty()) {
//            result = result.stream()
//                    .filter(e -> e.getEmploymentType().equalsIgnoreCase(employmentType))
//                    .collect(Collectors.toList());
//        }
        if (employmentType != null && !employmentType.isEmpty()) {
            EmploymentType typeEnum = EmploymentType.valueOf(employmentType.toUpperCase());

            result = result.stream()
                    .filter(e -> e.getEmploymentType() == typeEnum)
                    .collect(Collectors.toList());
        }
        return result;
    }
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    //given by suraj
    @Override
    public List<Employee> getEmployeesUnderManager(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }


}

