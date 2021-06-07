package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Document;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Document> getAllDocument() {
        return documentService.getAllDocuments();
    }

    @Secured("ROLE_USER")
    @Cacheable("document")
    @GetMapping("/{id}")
    public @ResponseBody
    Document getDocumentById(@PathVariable Integer id) throws ResourceNotFoundException {
        return documentService.getDocumentById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Document addNewDocument(@RequestBody Document document) {
        return documentService.addNewDocument(document);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteDocumentById(@PathVariable Integer id) {
        documentService.deleteDocumentById(id);
    }
}
