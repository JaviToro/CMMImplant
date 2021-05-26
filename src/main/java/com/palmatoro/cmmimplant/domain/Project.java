package com.palmatoro.cmmimplant.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String name;

    public enum ProjectType {
        WATERFALL,
        AGILE
    }

    private ProjectType projectType;

    private Date startDate;

    private Date endDate;

    // Relationships

    //private List<User> users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RiskAndOpportunity> risksAndOpportunities;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Analysis> analysis;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Audit> audits;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Improvement> improvements;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LessonLearnt> lessonsLearnt;

    // Getters and Setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectType getProjectType() {
        return this.projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /*public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    // Relationships

    // RiskAndOpportunity
    public List<RiskAndOpportunity> getRisksAndOpportunities(){
        return this.risksAndOpportunities;
    }

    public void setRisksAndOpportunities(List<RiskAndOpportunity> risksAndOpportunities){
        this.risksAndOpportunities = risksAndOpportunities;
    }

    // Analysis
    public List<Analysis> getAnalysis(){
        return this.analysis;
    }

    public void setAnalysis(List<Analysis> analysis){
        this.analysis = analysis;
    }

    // Audit
    public List<Audit> getAudits(){
        return this.audits;
    }

    public void setAudits(List<Audit> audits){
        this.audits = audits;
    }

    // Document
    public List<Document> getDocuments(){
        return this.documents;
    }

    public void setDocuments(List<Document> documents){
        this.documents = documents;
    }

    // Improvement
    public List<Improvement> getImprovements(){
        return this.improvements;
    }

    public void setImprovements(List<Improvement> improvements){
        this.improvements = improvements;
    }

    // LessonLearnt
    public List<LessonLearnt> getLessonsLearnt(){
        return this.lessonsLearnt;
    }

    public void setLessonsLearnt(List<LessonLearnt> lessonsLearnt){
        this.lessonsLearnt = lessonsLearnt;
    }
}
