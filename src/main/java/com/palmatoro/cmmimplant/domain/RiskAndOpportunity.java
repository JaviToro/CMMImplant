package com.palmatoro.cmmimplant.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
        TEMPLATE
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
    private Date identificationDate;
    private RiskProbability probability;
    private Impact impact;
    private String treshold;
    private String consequences;
    private String actionPlan;
    private Status status;
    private RiskMonitorization monitorization;
    private Date lastRevisionDate;
    private Date closeDate;
    private String observations;
    private Priority priority;


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

    public String getTreshold() {
        return treshold;
    }

    public void setTreshold(String treshold) {
        this.treshold = treshold;
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
        if(this.getProbability()==RiskProbability.HIGH && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.INMEDIATLY);
        }else if(this.getProbability()==RiskProbability.HIGH && this.getImpact()==Impact.MID){
            this.setPriority(Priority.HIGH);
        }else if(this.getProbability()==RiskProbability.MID && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.HIGH);
        }else if(this.getProbability()==RiskProbability.HIGH && this.getImpact()==Impact.LOW){
            this.setPriority(Priority.MIDHIGH);
        }else if(this.getProbability()==RiskProbability.LOW && this.getImpact()==Impact.HIGH){
            this.setPriority(Priority.MIDHIGH);
        }else if(this.getProbability()==RiskProbability.MID && this.getImpact()==Impact.MID){
            this.setPriority(Priority.MID);
        }else if(this.getProbability()==RiskProbability.MID && this.getImpact()==Impact.LOW){
            this.setPriority(Priority.MIDLOW);
        }else if(this.getProbability()==RiskProbability.LOW && this.getImpact()==Impact.MID){
            this.setPriority(Priority.MIDLOW);
        }else{
            this.setPriority(Priority.LOW);
        }
    }

    
}
