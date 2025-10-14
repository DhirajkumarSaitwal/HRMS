package com.example.User.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "holiday_master")
@EntityListeners(AuditingEntityListener.class)
public class HolidayCalendar {

    @Id
    @GeneratedValue
    @Column(name = "holiday_id", updatable = false, nullable = false)
    private Long holidayId;

    @Column(name = "holiday_name", nullable = false)
    private String holidayName;

    @Column(name = "holiday_date", nullable = false)
    private LocalDate holidayDate;

    @Column(name = "region")
    private String region;

    @Column(name = "is_optional")
    private Boolean isOptional;

    @Enumerated(EnumType.STRING)
    @Column(name = "holiday_type", nullable = false)
    private HolidayType holidayType; // Enum: National, Festival, Optional, Custom

    @Column(name = "description", length = 255)
    private String description;

    // ======= Auditing fields =======
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}


