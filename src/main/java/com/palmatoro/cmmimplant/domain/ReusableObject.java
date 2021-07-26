package com.palmatoro.cmmimplant.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReusableObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    private String title;
    private String description;
    private String objectType;
    private Date receptionDate;
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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
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

    public ReusableObject(Integer id, String identifier, String title, String description, String objectType, Date receptionDate, String link, String observations){
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.objectType = objectType;
        this.receptionDate = receptionDate;
        this.link = link;
        this.observations = observations;
    }
}
