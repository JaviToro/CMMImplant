package com.palmatoro.cmmimplant.service;

import com.palmatoro.cmmimplant.domain.Document;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.DocumentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentService {

    private DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public Iterable<Document> getAllDocuments(){
        return documentRepository.findAll();
    }

    public Document getDocumentById(Integer id){
        Document document = documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No document found on ID: " + id));
        return document;
    }

    @Transactional
    public Document addNewDocument(Document document){
        return documentRepository.save(document);
    }

    @Transactional
    public Document editDocument(Integer id, String identifier, String title, String direction, String practiceAreas, String observations){
        Document document = documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No document found on ID: " + id));

        document.setIdentifier(identifier);
        document.setTitle(title);
        document.setDirection(direction);
        document.setPracticeAreas(practiceAreas);
        document.setObservations(observations);

        return documentRepository.save(document);
    }

    @Transactional
    public void deleteDocumentById(Integer id){
        Document document = documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No document found on ID: " + id));
        documentRepository.delete(document);
    }
    
}
