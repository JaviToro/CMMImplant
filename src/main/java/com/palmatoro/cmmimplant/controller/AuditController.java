package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Audit;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AuditService;

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
@RequestMapping(path = "/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Audit> getAllAudit() {
        return auditService.getAllAudits();
    }

    @Secured("ROLE_USER")
    @Cacheable("audit")
    @GetMapping("/{id}")
    public @ResponseBody
    Audit getAuditById(@PathVariable Integer id) throws ResourceNotFoundException {
        return auditService.getAuditById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Audit addNewAudit(@RequestBody Audit audit) {
        return auditService.addNewAudit(audit);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteAuditById(@PathVariable Integer id) {
        auditService.deleteAuditById(id);
    }
}
