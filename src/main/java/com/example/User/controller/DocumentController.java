package com.example.User.controller;


import com.example.User.entity.Document;
import com.example.User.entity.DocumentStatus;
import com.example.User.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("documentType") String documentType)

            throws Exception {
        Document doc = documentService.uploadDocument(file, employeeId, uploadedBy, documentType);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Document>> getDocumentsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(employeeId));
    }

    @PutMapping("/{employeeId}/verify")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<Document>> verifyAllDocumentsByEmployee(
            @PathVariable Long employeeId,
            @RequestParam(required = false) String remarks) {
        List<Document> updatedDocs = documentService.verifyAllDocumentsByEmployee(employeeId, remarks);
        return ResponseEntity.ok(updatedDocs);
    }

    @PutMapping("/{employeeId}/reject")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<Document>> rejectDocument(
            @PathVariable Long employeeId,
            @RequestParam(required = true) String remarks) {
        List<Document> updatedDocs = documentService.rejectAllDocumentsByEmployee(employeeId, remarks);
        return ResponseEntity.ok(updatedDocs);
    }

    //  Delete single document
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    //  Delete all by employee
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Void> deleteDocumentsByEmployee(@PathVariable Long employeeId) {
        documentService.deleteDocumentsByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}

