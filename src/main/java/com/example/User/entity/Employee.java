package com.example.User.entity;

//import com.example.User.enums.EmploymentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {    //created by hamad task2

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    @Column(unique = true, nullable = false)
    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    private String bloodGroup;

    private LocalDate dateOfJoining;

    private String designation;

    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    private String status = "ACTIVE";

    //Reporting Manager (linked to User table)
//    @ManyToOne
//    @JoinColumn(name = "reporting_manager_id", referencedColumnName = "id", nullable = true)
//    private User reportingManager;
//
//    // HR Business Partner (linked to User table)
//    @ManyToOne
//    @JoinColumn(name = "hrbp_id", referencedColumnName = "id")
//    private User hrbp;


    // Reporting Manager (linked to User table)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_manager_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User reportingManager;

    // HR Business Partner (linked to User table)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hrbp_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User hrbp;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

