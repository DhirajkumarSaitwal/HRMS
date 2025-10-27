package com.example.User.controller;
import com.example.User.entity.AuditLog;
import com.example.User.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/compliance/audit/logs")
@RequiredArgsConstructor
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<AuditLog> getAllLogs() {
        return auditLogService.getAllLogs();
    }

    @GetMapping("/{entityName}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<AuditLog> getLogsByEntity(@PathVariable String entityName) {
        return auditLogService.getLogsByEntity(entityName);
    }


    @GetMapping("/entity/{entityName}/{entityId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<AuditLog> getLogsByEntityAndId(@PathVariable String entityName,
                                               @PathVariable String entityId) {
        return auditLogService.getLogsByEntityAndId(entityName, entityId);
    }


    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<AuditLog> searchLogs(
            @RequestParam(required = false) String performedBy,
            @RequestParam(required = false) String action,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return auditLogService.advancedSearch(performedBy, action, from, to);
    }


    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> exportLogs() {
        List<AuditLog> logs = auditLogService.getAllLogs();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("auditId,entityName,entityId,action,performedBy,performedAt,oldValue,newValue,changeSummary,ipAddress,userAgent,remarks\n");

        for (AuditLog log : logs) {
            csvBuilder.append(log.getAuditLogId() != null ? log.getAuditLogId() : "").append(",")
                    .append(log.getEntityName() != null ? log.getEntityName() : "").append(",")
                    .append(log.getEntityId() != null ? log.getEntityId() : "").append(",")
                    .append(log.getAction() != null ? log.getAction() : "").append(",")
                    .append(log.getPerformedBy() != null ? log.getPerformedBy() : "").append(",")
                    .append(log.getTimestamp() != null ? log.getTimestamp() : "").append(",")
                    .append(log.getOldValue() != null ? log.getOldValue() : "").append(",")
                    .append(log.getNewValue() != null ? log.getNewValue() : "").append(",")
                    .append(log.getChangeSummary() != null ? log.getChangeSummary() : "").append(",")
                    .append(log.getIpAddress() != null ? log.getIpAddress() : "").append(",")
                    .append(log.getUserAgent() != null ? log.getUserAgent() : "").append(",")
                    .append(log.getRemarks() != null ? log.getRemarks() : "").append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audit_logs.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvBuilder.toString());
    }


    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> saveAuditLog(@RequestBody AuditLog log) {
        String performedBy = log.getPerformedBy() != null ? log.getPerformedBy() : "SYSTEM";
        String action = log.getAction() != null ? log.getAction() : "UNKNOWN";
        String details = log.getDetails() != null && !log.getDetails().isBlank() ? log.getDetails() : "No details provided";

        auditLogService.saveAudit(
                performedBy,
                action,
                details,
                log.getEntityName(),
                log.getEntityId(),
                log.getOldValue(),
                log.getNewValue(),
                log.getChangeSummary(),
                log.getIpAddress(),
                log.getUserAgent(),
                log.getRemarks()
        );

        return ResponseEntity.ok("Audit log saved successfully");
    }

    @PostMapping("/superadmin/token")
    public ResponseEntity<String> createSuperAdminToken(HttpServletRequest request) {
        String superadminUsername = "superadmin";
        String superadminId = "1";
        String token = "xyz123"; // Example token

        auditLogService.saveAudit(
                superadminUsername,
                "TOKEN_CREATION",
                "Superadmin token generated successfully",
                "User",
                superadminId,
                null,
                token,
                "Superadmin token creation",
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                "Superadmin token creation event"
        );

        return ResponseEntity.ok("Superadmin token generated: " + token);
    }
}
