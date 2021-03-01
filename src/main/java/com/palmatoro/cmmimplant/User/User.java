package com.palmatoro.cmmimplant.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)

  private Integer id;

  private String name;

  private String surname;

  private String acronym;

  public enum UserRole {
    PROJECT_MANAGER,
    ANALIST,
    DEVELOPER
      }

  private UserRole role;

  private String email;

  private String password;

  public Integer getId() {
    return id;
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
    return this.role;
  }

  public void setUserRole(UserRole role) {
    this.role = role;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String password() {
      return this.password;
  }

  public void setPassword(String password) {
      this.password = password;
  }
}