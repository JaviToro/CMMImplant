package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Document;
import com.palmatoro.cmmimplant.repository.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/document")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
