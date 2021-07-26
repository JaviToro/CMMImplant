package com.palmatoro.cmmimplant.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;

@Entity
public class LessonLearnt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    private String title;
    private String description;
    private Date receptionDate;
    private Date communicationDate;
    private String link;
    private String observations;

    // Relationships ----------------------------------------

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    // Getters and Setters ----------------------------------------

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

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getCommunicationDate() {
        return communicationDate;
    }

    public void setCommunicationDate(Date communicationDate) {
        this.communicationDate = communicationDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
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

    public LessonLearnt(Integer id, String identifier, String title, String description, Date receptionDate, Date communicationDate, String link, String observations){
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.receptionDate = receptionDate;
        this.communicationDate = communicationDate;
        this.link = link;
        this.observations = observations;
    }
}
