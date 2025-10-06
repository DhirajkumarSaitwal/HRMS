package com.example.User.service;

import com.example.User.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    //Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType) throws IOException;

    // Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType, String Status) throws IOException, IOException;

    //  Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType, String Status) throws IOException, IOException;

    Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType) throws IOException, IOException;

    Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType, String Status) throws IOException, IOException;

    //Document uploadDocument(MultipartFile file, Long employeeId, Long uploadedBy, String documentType) throws Exception;
    List<Document> getDocumentsByEmployee(Long employeeId);
    // Document verifyDocument(Long documentId, String remarks);

    List<Document> verifyAllDocumentsByEmployee(Long employeeId, String remarks);

    // Document rejectDocument(Long documentId, String remarks);

    List<Document> rejectAllDocumentsByEmployee(Long employeeId, String remarks);

    void deleteDocument(Long documentId);

    void deleteDocumentsByEmployeeId(Long employeeId);
}

