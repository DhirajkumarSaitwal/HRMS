package com.example.User.service;

import com.example.User.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {

    void saveAudit(String performedBy, String action, String details,
                   String entityName, String entityId,
                   String oldValue, String newValue, String changeSummary,
                   String ipAddress, String userAgent, String remarks);

    List<AuditLog> getAllLogs();

    List<AuditLog> getLogsByEntity(String entityName);

    List<AuditLog> getLogsByEntityAndId(String entityName, String entityId);

    List<AuditLog> advancedSearch(String performedBy, String action,
                                  LocalDateTime from, LocalDateTime to);

    void logEvent(String username, String action); // Optional generic
}
