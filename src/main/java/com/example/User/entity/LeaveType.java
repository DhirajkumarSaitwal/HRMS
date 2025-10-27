package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTypeId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    private Integer annualLimit;

    private Boolean carryForward = false;

    private Boolean encashable = false;

    private Boolean isActive = true;
}

