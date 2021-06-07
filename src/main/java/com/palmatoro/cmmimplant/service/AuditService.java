package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Audit;
import com.palmatoro.cmmimplant.domain.Status;
import com.palmatoro.cmmimplant.domain.Audit.AuditType;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.AuditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditService {

    private AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository){
        this.auditRepository = auditRepository;
    }

    public Iterable<Audit> getAllAudits(){
        return auditRepository.findAll();
    }

    public Audit getAuditById(Integer id){
        Audit audit = auditRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No audit found on ID: " + id));
        return audit;
    }

    @Transactional
    public Audit addNewAudit(Audit audit){
        return auditRepository.save(audit);
    }

    @Transactional
    public Audit editAudit(Integer id, String identifier, AuditType type, String auditIdentifier, Date auditDate, Status status, Integer initialErrors, Integer actualErrors, String direction, String observations){
        Audit audit = auditRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No audit found on ID: " + id));

        audit.setIdentifier(identifier);
        audit.setType(type);
        audit.setAuditIdentifier(auditIdentifier);
        audit.setStatus(status);
        audit.setInitialErrors(initialErrors);
        audit.setActualErrors(actualErrors);
        audit.setDirection(direction);
        audit.setObservations(observations);

        return auditRepository.save(audit);
    }

    @Transactional
    public void deleteAuditById(Integer id){
        Audit audit = auditRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No audit found on ID: " + id));
        auditRepository.delete(audit);
    }
    
}
