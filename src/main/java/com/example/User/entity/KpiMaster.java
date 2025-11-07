package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "kpi_master",
        uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "role", "kpi_name"})
)
public class KpiMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kpiId;

    // Department Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, length = 100)
    private String kpiName;

    @Column(length = 500)
    private String kpiDescription;

    @Column(nullable = false)
    private Double weightage;

    //  New Field — Soft Delete
    @Column(nullable = false)
    private Boolean isActive = true;

    // Audit Fields
    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdBy == null) this.createdBy = "ADMIN";
        if (this.updatedBy == null) this.updatedBy = "ADMIN";
        if (this.isActive == null) this.isActive = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = "ADMIN";
    }
}
