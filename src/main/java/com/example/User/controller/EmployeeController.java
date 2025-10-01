package com.example.User.controller;

import com.example.User.entity.Employee;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    // ----------------- Create Employee -----------------
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return ResponseEntity.ok(created);
    }

    // ----------------- Update Employee -----------------
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ----------------- Delete Employee -----------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    // ----------------- View Employee by ID -----------------
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') or hasRole('HR') and @employeeSecurity.isTeamMember(#id, authentication)) or (hasRole('EMPLOYEE') and @employeeSecurity.isSelf(#id, authentication))")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


//    // ----------------- View All Employees -----------------
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
//  Becouse of manger can view all employee realted to manager
//@GetMapping("/all")
//@PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
//public ResponseEntity<List<Employee>> getAllEmployees(Authentication authentication) {
//    // Fetch logged-in user's roles
//    List<Employee> employees;
//
//    boolean isManager = authentication.getAuthorities().stream()
//            .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
//
//    if (isManager) {
//        // Manager: fetch team members only
//        Long managerId = employeeService.getLoggedInEmployeeId(org.apache.tomcat.util.net.openssl.ciphers.Authentication.ANY); // implement in service
//        employees = employeeService.getTeamMembers(managerId);
//    } else {
//        // Admin / HR: fetch all employees
//        employees = employeeService.getAllEmployees();
//    }
//
//
//    return ResponseEntity.ok(employees);
//}
    //----------



    // ----------------- Search Employees -----------------
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation
    ) {
        return ResponseEntity.ok(employeeService.searchEmployees
                (name, email, department, designation));
    }

    // ----------------- Filter Employees -----------------
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<List<Employee>> filterEmployees(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String employmentType
    ) {
        return ResponseEntity.ok(employeeService.filterEmployees(status, department, employmentType));
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Employee> getEmployeesByManager(@PathVariable Long managerId) {
        return employeeService.getEmployeesUnderManager(managerId);
    }
}