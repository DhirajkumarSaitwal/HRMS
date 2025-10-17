package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "benefit_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenefitMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_id", updatable = false, nullable = false)
    private Long benefitId;

    @Column(name = "benefit_name", nullable = false, unique = true, length = 100)
    private String benefitName;

    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_type", nullable = false)
    private BenefitType benefitType;

    @Column(length = 255)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;
@CreationTimestamp
    private LocalDateTime createdAt;
@UpdateTimestamp
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;


    public enum BenefitType {
        Insurance, PF, ESI, Gratuity, Other
    }
}

