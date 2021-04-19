package com.palmatoro.cmmimplant.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    public enum AuditType{
        PROJECT,
        PEER_REVIEW,
        CONFIGURATION
    }

    private AuditType type;
    private String auditIdentifier;
    private Date auditDate;
    private User auditor;
    private User audited;
    private Status status;
    private Integer initialErrors;
    private Integer actualErrors;
    private String direction;
    private String observations;


    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public AuditType getType() {
        return type;
    }
    public void setType(AuditType type) {
        this.type = type;
    }
    public String getAuditIdentifier() {
        return auditIdentifier;
    }
    public void setAuditIdentifier(String auditIdentifier) {
        this.auditIdentifier = auditIdentifier;
    }
    public Date getAuditDate() {
        return auditDate;
    }
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
    public User getAuditor() {
        return auditor;
    }
    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }
    public User getAudited() {
        return audited;
    }
    public void setAudited(User audited) {
        this.audited = audited;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Integer getInitialErrors() {
        return initialErrors;
    }
    public void setInitialErrors(Integer initialErrors) {
        this.initialErrors = initialErrors;
    }
    public Integer getActualErrors() {
        return actualErrors;
    }
    public void setActualErrors(Integer actualErrors) {
        this.actualErrors = actualErrors;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getObservations() {
        return observations;
    }
    public void setObservations(String observations) {
        this.observations = observations;
    }

  
}
