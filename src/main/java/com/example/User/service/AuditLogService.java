package com.example.User.service;

import com.example.User.entity.AuditLog;
import com.example.User.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;



    public void logEvent(String username, String action) {

        if(username == null || username.isBlank()) {
            username = "SYSTEM";  // default if blank
        }

        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setPerformedBy(username);
        log.setDetails(action + " performed by " + username);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);

    }
}
