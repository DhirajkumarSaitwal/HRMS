package com.example.User.serviceimpl;

import com.example.User.entity.Document;
import com.example.User.entity.DocumentStatus;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.DocumentRepository;
import com.example.User.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;



@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private  DocumentRepository documentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    private Document getDocumentById(Long documentId) {
        return null;
    }


    @Override
    public Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType) throws IOException, IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path fileStorage = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(fileStorage);

        Path targetLocation = fileStorage.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Document doc = new Document();
        doc.setEmployeeId(employeeId);
        doc.setUploadedBy(uploadedBy);
        doc.setDocumentType(documentType);
        doc.setFileName(fileName);
        doc.setFilePath(targetLocation.toString());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setStatus(String.valueOf(DocumentStatus.PENDING));

        return documentRepository.save(doc);
    }


    @Override
    public List<Document> getDocumentsByEmployee(Long employeeId) {
        return documentRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Document> verifyAllDocumentsByEmployee(Long employeeId, String remarks) {
        List<Document> docs = documentRepository.findByEmployeeId(employeeId);

        if (docs.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employeeId " + employeeId);
        }

        for (Document doc : docs) {
            doc.setStatus(String.valueOf(DocumentStatus.VERIFIED));
            doc.setRemarks(remarks);
            doc.setVerifiedAt(LocalDateTime.now());
        }

        return documentRepository.saveAll(docs);
    }

    @Override
    public List<Document> rejectAllDocumentsByEmployee(Long employeeId, String remarks) {
        List<Document> docs = documentRepository.findByEmployeeId(employeeId);

        if (docs.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employeeId " + employeeId);
        }

        for (Document doc : docs) {
            doc.setStatus(String.valueOf(DocumentStatus.REJECTED));
            doc.setRemarks(remarks);
            doc.setVerifiedAt(LocalDateTime.now());
        }

        return documentRepository.saveAll(docs);
    }

    @Override
    public void deleteDocument(Long documentId) {
       Document docs = documentRepository.findByDocumentId(documentId);
     //   Document doc = getDocumentById(documentId);
        documentRepository.delete(docs);
    }



    @Override
    public void deleteDocumentsByEmployeeId(Long employeeId) {
        List<Document> docs = documentRepository.findByEmployeeId(employeeId);
        documentRepository.deleteAll(docs);
    }





}


