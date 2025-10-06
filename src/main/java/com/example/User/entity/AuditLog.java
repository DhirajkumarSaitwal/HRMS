package com.example.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditLogId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = false, nullable = false)
    private String username;

    // LOGIN, LOGOUT, PASSWORD_CHANGE
    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

}
