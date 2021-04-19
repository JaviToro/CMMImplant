package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Audit;
import com.palmatoro.cmmimplant.repository.AuditRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/audit")
public class AuditController {
    @Autowired
    private AuditRepository auditRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Audit> getAllAudits() {
        return auditRepository.findAll();
    }
}
