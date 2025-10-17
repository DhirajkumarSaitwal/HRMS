package com.example.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @NotBlank
    @Column(nullable = false)
    private String username= "SYSTEM";

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "entity_name", length = 100)
    private String entityName;  // e.g., ComplianceMaster, EmployeeComplianceMapping

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "action", length = 50)
    private String action;  // e.g., CREATE, UPDATE, DELETE

    @Column(name = "performed_by", length = 100)
    private String performedBy;

    @CreationTimestamp
    @Column(name = "performed_at")
    private LocalDateTime performedAt;

    @Column(columnDefinition = "TEXT")
    private String details;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
