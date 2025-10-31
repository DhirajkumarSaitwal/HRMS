package com.example.User.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary_structure_master")
public class SalaryStructureMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long structureId;

    @Column(unique = true, length = 100, nullable = false)
    private String structureName;

    private Double basicPay;
    private Double hra;
    private Double variablePay;
    private Double otherAllowances;
    private Double deductions;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    private String createdBy;
    private String updatedBy;



}

