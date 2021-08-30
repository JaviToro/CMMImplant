package com.palmatoro.cmmimplant.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class RiskAndOpportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    public enum RiskType{
        RISK,
        OPPORTUNITY,
        PROBLEM
    }

    public enum RiskCategory{
        TECHNOLOGICAL,
        CONTRACTUAL,
        MANAGING,
        PROCESS,
        TEMPLATE,
        OTHER
    }

    public enum RiskProbability{
        HIGH,
        MID,
        LOW
    }

    public enum RiskMonitorization{
        DAILY,
        WEEKLY,
        QUINCENAL,
        MONTHLY
    }

    private String identifier;    
    private RiskType type;
    private RiskCategory category;
    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date identificationDate;

    private RiskProbability probability;
    private Impact impact;
    private String threshold;
    private String consequences;
    private String actionPlan;
    private Status status;
    private RiskMonitorization monitorization;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date lastRevisionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date closeDate;
    
    private String observations;
    private Priority priority;

    // Relationships ----------------------------------------

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    // Getters and Setters ----------------------------------------


    public Integer getId() {
        return this.id;
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

    public RiskType getType() {
        return type;
    }

    public void setType(RiskType type) {
        this.type = type;
    }

    public RiskCategory getCategory() {
        return category;
    }

    public void setCategory(RiskCategory category) {
        this.category = category;
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

    public Date getIdentificationDate() {
        return identificationDate;
    }

    public void setIdentificationDate(Date identificationDate) {
        this.identificationDate = identificationDate;
    }

    public RiskProbability getProbability() {
        return probability;
    }

    public void setProbability(RiskProbability probability) {
        this.probability = probability;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getConsequences() {
        return consequences;
    }

    public void setConsequences(String consequences) {
        this.consequences = consequences;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RiskMonitorization getMonitorization() {
        return monitorization;
    }

    public void setMonitorization(RiskMonitorization monitorization) {
        this.monitorization = monitorization;
    }

    public Date getLastRevisionDate() {
        return lastRevisionDate;
    }

    public void setLastRevisionDate(Date lastRevisionDate) {
        this.lastRevisionDate = lastRevisionDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
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
        this.priority = priority;
    }

    // Relationships ----------------------------------------

    // Project (*..1)
    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // Constructors ----------------------------------------

    public RiskAndOpportunity(Integer id, String identifier, RiskType type, RiskCategory category, String title, String description, Date identificationDate, RiskProbability probability, Impact impact, String threshold, String consequences,
        String actionPlan, Status status, RiskMonitorization monitorization, Date lastRevisionDate, Date closeDate, String observations){
        this.id = id;
        this.identifier = identifier;
        this.type = type;
        this.category = category;
        this.title = title;
        this.description = description;
        this.identificationDate = identificationDate;
        this.probability = probability;
        this.impact = impact;
        this.threshold = threshold;
        this.consequences = consequences;
        this.actionPlan = actionPlan;
        this.status = status;
        this.monitorization = monitorization;
        this.lastRevisionDate = lastRevisionDate;
        this.closeDate = closeDate;
        this.observations = observations;

        if(probability==RiskProbability.HIGH && impact==Impact.HIGH){
            this.setPriority(Priority.INMEDIATLY);
        }else if(probability==RiskProbability.HIGH && impact==Impact.MID){
            this.setPriority(Priority.HIGH);
        }else if(probability==RiskProbability.MID && impact==Impact.HIGH){
            this.setPriority(Priority.HIGH);
        }else if(probability==RiskProbability.HIGH && impact==Impact.LOW){
            this.setPriority(Priority.MIDHIGH);
        }else if(probability==RiskProbability.LOW && impact==Impact.HIGH){
            this.setPriority(Priority.MIDHIGH);
        }else if(probability==RiskProbability.MID && impact==Impact.MID){
            this.setPriority(Priority.MID);
        }else if(probability==RiskProbability.MID && impact==Impact.LOW){
            this.setPriority(Priority.MIDLOW);
        }else if(probability==RiskProbability.LOW && impact==Impact.MID){
            this.setPriority(Priority.MIDLOW);
        }else{
            this.setPriority(Priority.LOW);
        }
    }

    public RiskAndOpportunity(){
        
    }
}
