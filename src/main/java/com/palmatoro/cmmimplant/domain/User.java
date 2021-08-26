package com.palmatoro.cmmimplant.domain;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String surname;

    private String acronym;

    public enum UserRole {
        ADMIN, PM, USER
    }

    private UserRole userRole;

    private String email;

    private String password;

    // Relationships ----------------------------------------------

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole role) {
        this.userRole = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Relationships

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // Constructors --------------------------

    public User() {

    }

    public User(Integer id, String name, String surname, String acronym, UserRole userRole, String email,
            String password, Project project) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.acronym = acronym;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        this.project = project;
    }

}