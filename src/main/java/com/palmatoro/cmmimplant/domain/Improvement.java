package com.palmatoro.cmmimplant.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Improvement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public enum ImprovementStatus{
        RECEIVED,
        APPROVED,
        DENIED,
        DOING,
        FINISHED,
        EVALUATED
    }

    private String identifier;

    private String title;
    private String description;
    private ImprovementStatus status;
    private Impact impact;
    private Double percentage;
    private String estimatedEffort;
    private String realEffort;
    private Date receptionDate;
    private Date approvalDate;
    private Date estimatedImplementation;
    private Date realImplementation;
    private Date evaluationDate;
    private Double score;
    private String observations;

    private Priority priority;

    // Relationships --------------------------------------

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "FK_ResponsableImporvement")
    private User responsable;

    // Getters and Setters ---------------------------------

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImprovementStatus getStatus() {
        return status;
    }

    public void setStatus(ImprovementStatus status) {
        this.status = status;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getEstimatedEffort() {
        return estimatedEffort;
    }

    public void setEstimatedEffort(String estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    public String getRealEffort() {
        return realEffort;
    }

    public void setRealEffort(String realEffort) {
        this.realEffort = realEffort;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getEstimatedImplementation() {
        return estimatedImplementation;
    }

    public void setEstimatedImplementation(Date estimatedImplementation) {
        this.estimatedImplementation = estimatedImplementation;
    }

    public Date getRealImplementation() {
        return realImplementation;
    }

    public void setRealImplementation(Date realImplementation) {
        this.realImplementation = realImplementation;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if(this.getPercentage()>=75.0 && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.INMEDIATLY);
        }else if(this.getPercentage()>=75.0 && this.getImpact()==Impact.MID){
            this.setPriority(Priority.HIGH);
        }else if(this.getPercentage()<75.0 && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.HIGH);
        }else if(this.getPercentage()>=75.0 && this.getImpact()==Impact.LOW){
            this.setPriority(Priority.MIDHIGH);
        }else if(this.getPercentage()<=50.0 && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.MIDHIGH);
        }else if(this.getPercentage()<75.0 && this.getImpact()==Impact.MID){
            this.setPriority(Priority.MID);
        }else if(this.getPercentage()<75.0 && this.getImpact()==Impact.LOW){
            this.setPriority(Priority.MIDLOW);
        }else if(this.getPercentage()<=50.0 && this.getImpact()==Impact.MID){
            this.setPriority(Priority.MIDLOW);
        }else{
            this.setPriority(Priority.LOW);
        }
    }

    // Relationships --------------------------------------------------

    // Project (*..1)
    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }


    // User (*..1)
    public User getResponsable() {
        return responsable;
    }

    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }

    // Constructors -------------------------------------

    public Improvement(Integer id, String title, String description, ImprovementStatus status, Impact impact, Double percentage, String estimatedEffort, String realEffort, 
        Date receptionDate, Date approvalDate, Date estimatedImplementation, Date realImplementation, Date evaluationDate, Double score, String observations){
            this.id = id;
            this.title = title;
            this.description = description;
            this.status = status;
            this.impact = impact;
            this.percentage = percentage;
            this.estimatedEffort = estimatedEffort;
            this.realEffort = realEffort;
            this.receptionDate = receptionDate;
            this.approvalDate = approvalDate;
            this.estimatedEffort = estimatedEffort;
            this.realImplementation = realImplementation;
            this.evaluationDate = evaluationDate;
            this.score = score;
            this.observations = observations;
    }

    public Improvement() {
    }

}
