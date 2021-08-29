package com.palmatoro.cmmimplant.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String identifier;

    private String title;
    private String direction;
    private String practiceAreas;
    private String observations;

    // Relationships -------------------------------------------

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    // Getters and Setters --------------------------------------

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
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getPracticeAreas() {
        return practiceAreas;
    }
    public void setPracticeAreas(String practiceAreas) {
        this.practiceAreas = practiceAreas;
    }
    public String getObservations() {
        return observations;
    }
    public void setObservations(String observations) {
        this.observations = observations;
    }

    // Relationships ----------------------------------------

    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // Constructors -----------------------------------------

    public Document(Integer id, String identifier, String title, String direction, String practiceAreas, String observations){
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.direction = direction;
        this.practiceAreas = practiceAreas;
        this.observations = observations;
    }
    public Document() {
    }
  
}
