package com.example.User.security;

import com.example.User.entity.Employee;
import com.example.User.entity.User;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public SecurityService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean canAccessEmployee(Long employeeId, Authentication authentication) {
        String username = authentication.name();


        Employee loggedInUser = employeeRepository.findByUsername(username).orElse(null);
        if (loggedInUser == null) {
            return false;
        }

        String role = loggedInUser.getRole().name();

        if ("EMPLOYEE".equalsIgnoreCase(role)) {
            return loggedInUser.getId().equals(employeeId);
        }

        return false;
    }

    public boolean canAccessHr(Long userId, Authentication authentication) {
        String username = authentication.name();


        User loggedInUser = userRepository.findByUsername(username).orElse(null);
        if (loggedInUser == null) {
            return false;
        }

        String role = loggedInUser.getRole().name();


        if ("HR".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) {
            return true;
        }
        return false;
    }
}
