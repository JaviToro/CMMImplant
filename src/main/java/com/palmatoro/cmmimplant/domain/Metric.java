package com.palmatoro.cmmimplant.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String identifier;

    private String name;
    private String formula;
    private Period period;
    private Double upperLimit;
    private Double lowerLimit;
    private String observations;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "FK_Project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "FK_ResponsableMetric")
    private User responsable;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Value> values;



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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormula() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }
    public Period getPeriod() {
        return period;
    }
    public void setPeriod(Period period) {
        this.period = period;
    }
    public Double getUpperLimit() {
        return upperLimit;
    }
    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }
    public Double getLowerLimit() {
        return lowerLimit;
    }
    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getObservations() {
        return observations;
    }
    public void setObservations(String observations) {
        this.observations = observations;
    }

    // Relationships ---------------------------------------------------------------------

    // Project (*..1)
    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    // User (*..1)
    public User getResponsable() {
        return responsable;
    }
    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }

    // Value (1..*)
    public List<Value> getValues(){
        return this.values;
    }

    public void setValues(List<Value> values){
        this.values = values;
    }
}
