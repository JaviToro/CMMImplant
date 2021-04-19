package com.palmatoro.cmmimplant.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    public enum AnalysisType{
        DECISION,
        CAUSAL
    }

    public enum AnalysisStatus{
        OPEN,
        FINISHED,
        EVALUATED
    }

    private AnalysisType type;
    private String analysisIdentifier;
    private AnalysisStatus status;
    private User responsable;
    private String direction;
    private Date date;
    private Date evaluationDate;
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
    public AnalysisType getType() {
        return type;
    }
    public void setType(AnalysisType type) {
        this.type = type;
    }
    public String getAnalysisIdentifier() {
        return analysisIdentifier;
    }
    public void setAnalysisIdentifier(String analysisIdentifier) {
        this.analysisIdentifier = analysisIdentifier;
    }
    public AnalysisStatus getStatus() {
        return status;
    }
    public void setStatus(AnalysisStatus status) {
        this.status = status;
    }
    public User getResponsable() {
        return responsable;
    }
    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getEvaluationDate() {
        return evaluationDate;
    }
    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public String getObservations() {
        return observations;
    }
    public void setObservations(String observations) {
        this.observations = observations;
    }    
  
}
