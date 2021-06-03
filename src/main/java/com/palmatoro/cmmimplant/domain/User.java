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
        PROJECT_MANAGER,
        ANALIST,
        DEVELOPER
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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public UserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRole role) {
        this.userRole = role;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Relationships

    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // Constructors --------------------------

    public User(Integer id, String name, String surname, String acronym, UserRole userRole, String email, String password){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.acronym = acronym;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
    }

}