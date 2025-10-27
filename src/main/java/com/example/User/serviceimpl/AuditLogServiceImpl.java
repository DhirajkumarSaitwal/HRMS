package com.example.User.serviceimpl;

import com.example.User.entity.AuditLog;
import com.example.User.repository.AuditLogRepository;
import com.example.User.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void saveAudit(String performedBy, String action, String details,
                          String entityName, String entityId,
                          String oldValue, String newValue, String changeSummary,
                          String ipAddress, String userAgent, String remarks) {

        AuditLog log = AuditLog.builder()
                .performedBy(performedBy != null ? performedBy : "SYSTEM")
                .username(performedBy != null ? performedBy : "SYSTEM")
                .action(action != null ? action : "UNKNOWN")
                .details(details != null ? details : "No details provided")
                .entityName(entityName)
                .entityId(entityId)
                .oldValue(oldValue != null ? oldValue : "N/A")
                .newValue(newValue != null ? newValue : "N/A")
                .changeSummary(changeSummary != null ? changeSummary : "N/A")
                .ipAddress(ipAddress != null ? ipAddress : "0.0.0.0")
                .userAgent(userAgent != null ? userAgent : "Unknown")
                .remarks(remarks != null ? remarks : "No remarks")
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public List<AuditLog> getLogsByEntity(String entityName) {
        return auditLogRepository.findByEntityName(entityName);
    }

    @Override
    public List<AuditLog> getLogsByEntityAndId(String entityName, String entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId);
    }

    @Override
    public List<AuditLog> advancedSearch(String performedBy, String action,
                                         LocalDateTime from, LocalDateTime to) {
        return auditLogRepository.search(performedBy, action, from, to);
    }

    @Override
    public void logEvent(String username, String action) {
        saveAudit(username, action, "Event logged", null, null,
                null, null, null, null, null, null);
    }
}
