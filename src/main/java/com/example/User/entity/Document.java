package com.example.User.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false)
    private Long employeeId; // FK to Employee table

    @Column(length = 50, nullable = false)
    private String documentType;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 500, nullable = false)
    private String filePath;


    @Column(nullable = false)
    private String uploadedBy; // FK to Employee/HR/Admin

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;


    @Column(nullable = false)
    private String status = "PENDING";

    @Lob
    private String remarks;


    @Column(nullable = false)
    private String storageType = "Local";

    @UpdateTimestamp
    private LocalDateTime verifiedAt;

//    @PrePersist
//    public void onUpload() {
//        this.uploadedAt = LocalDateTime.now();
//        if (this.status == null) {
//            this.status = DocumentStatus.PENDING;
//        }
   // }

    // Getters & Setters
}

