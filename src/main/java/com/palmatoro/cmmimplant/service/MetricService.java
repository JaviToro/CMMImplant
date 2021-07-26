package com.palmatoro.cmmimplant.service;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.domain.Period;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.MetricRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MetricService {

    private MetricRepository metricRepository;

    public MetricService(MetricRepository metricRepository){
        this.metricRepository = metricRepository;
    }

    public Iterable<Metric> getAllMetrics(){
        return metricRepository.findAll();
    }

    public Metric getMetricById(Integer id){
        Metric metric = metricRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No metric found on ID: " + id));
        return metric;
    }

    @Transactional
    public Metric addNewMetric(Metric metric){
        return metricRepository.save(metric);
    }

    @Transactional
    public Metric editMetric(Integer id, String identifier, String name, String formula, Period period, Double upperLimit, Double lowerLimit, String observations){
        Metric metric = metricRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No metric found on ID: " + id));

        metric.setIdentifier(identifier);
        metric.setName(name);
        metric.setFormula(formula);
        metric.setPeriod(period);
        metric.setUpperLimit(upperLimit);
        metric.setLowerLimit(lowerLimit);
        metric.setObservations(observations);
        
        return metricRepository.save(metric);
    }

    @Transactional
    public void deleteMetricById(Integer id){
        Metric metric = metricRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No metric found on ID: " + id));
        metricRepository.delete(metric);
    }
    
}
