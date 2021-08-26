package com.palmatoro.cmmimplant.domain;


import javax.persistence.*;

@Entity
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String identifier;

    private String moment;

    private Double amount;

    private String observation;

    // Relationships ----------------------------------------

    @ManyToOne
    @JoinColumn(name = "FK_Metric")
    private Metric metric;

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

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    // Relationships ----------------------------------------

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    // Constructor ----------------------------------------

    public Value() {

    }

    public Value(Integer id, String identifier, String moment, Double amount, String observation) {
        this.id = id;
        this.identifier = identifier;
        this.moment = moment;
        this.amount = amount;
        this.observation = observation;
    }
}
