package com.palmatoro.cmmimplant.domain;


import javax.persistence.*;

@Entity
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String identifier;

    private String moment;
    private Double value;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "FK_Metric")
    private Metric metric;

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
    public String getMoment() {
        return moment;
    }
    public void setMoment(String moment) {
        this.moment = moment;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    // Relationships

    public Metric getMetric(){
        return metric;
    }

    public void setMetric(Metric metric){
        this.metric = metric;
    }
     
}
