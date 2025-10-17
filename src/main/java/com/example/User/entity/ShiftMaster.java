package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "shift_master", uniqueConstraints = @UniqueConstraint(columnNames = "shift_name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftMaster {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "shift_id")
        private Long shiftId;

        @Column(name = "shift_name", nullable = false)
        private String shiftName;

        @Column(name = "start_time", nullable = false)
        private LocalTime startTime;

        @Column(name = "end_time", nullable = false)
        private LocalTime endTime;

        @Column(name = "week_off")
        private String weekOff; // e.g., "SATURDAY,SUNDAY"

        @Column(name = "is_active")
        private Boolean isActive = true;

        @Column(name = "grace_minutes")
        private Integer graceMinutes = 0;

        @Column(name = "break_minutes")
        private Integer breakMinutes = 0;

        @Column(name = "is_rotational")
        private Boolean isRotational = false;

        @Column(name = "is_overnight")
        private Boolean isOvernight = false;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "created_by")
        private String createdBy;

        @Column(name = "updated_by")
        private String updatedBy;
    }
