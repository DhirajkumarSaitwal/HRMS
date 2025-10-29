package com.example.User.repository;

import com.example.User.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByEntityName(String entityName);

    List<AuditLog> findByEntityNameAndEntityId(String entityName, String entityId);

    default List<AuditLog> search(String performedBy, String action, LocalDateTime from, LocalDateTime to) {
        return findAll().stream()
                .filter(log -> (performedBy == null || log.getPerformedBy().equalsIgnoreCase(performedBy)) &&
                        (action == null || log.getAction().equalsIgnoreCase(action)) &&
                        (from == null || !log.getTimestamp().isBefore(from)) &&
                        (to == null || !log.getTimestamp().isAfter(to)))
                .toList();
    }
}
