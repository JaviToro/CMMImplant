package com.palmatoro.cmmimplant.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    private String area;
    private Date date;

    // Relationships ---------------------

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Relationships --------------------------------

    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // Constructors ---------------------------------

    public Alert(Integer id, String identifier, String area, Date date){
        this.id = id;
        this.identifier = identifier;
        this.area = area;
        this.date = date;
    }
}
