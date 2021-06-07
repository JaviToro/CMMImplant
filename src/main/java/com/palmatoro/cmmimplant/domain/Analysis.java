package com.palmatoro.cmmimplant.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    private String direction;
    private Date date;
    private Date evaluationDate;
    private String observations;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "FK_ResponsableAnalysis")
    private User responsable;

    // Getters and Setters

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

    // Relationships ----------------------------------------

    // Project
    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // User
    public User getResponsable() {
        return responsable;
    }
    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }
  
    // Constructors ---------------------------------

    public Analysis(Integer id, String identifier, AnalysisType type, String analysisIdentifier, AnalysisStatus status, String direction, Date date, Date evaluationDate, String observations){
        this.id = id;
        this.identifier = identifier;
        this.type = type;
        this.analysisIdentifier = analysisIdentifier;
        this.status = status;
        this.direction = direction;
        this.date = date;
        this.evaluationDate = evaluationDate;
    }
}
