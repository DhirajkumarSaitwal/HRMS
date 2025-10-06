package com.example.User.repository;

import com.example.User.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByEmployeeId(Long employeeId);
    List<Document> findByStatus(String uploadedBy);

    Document findByDocumentId(Long documentId);
    // Void  findByDocumentId(Long documentId);
}
