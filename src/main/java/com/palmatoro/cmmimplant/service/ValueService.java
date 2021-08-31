package com.palmatoro.cmmimplant.service;

import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.ValueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValueService {

    private ValueRepository valueRepository;

    // Auxiliary Services --------------------------------------------
    @Autowired
    MetricService metricService;

    public ValueService(ValueRepository valueRepository){
        this.valueRepository = valueRepository;
    }

    public Iterable<Value> getAllValues(){
        return valueRepository.findAll();
    }

    public Value getValueById(Integer id){
        Value value = valueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No value found on ID: " + id));
        return value;
    }

    @Transactional
    public Value addNewValue(Value value){
        return valueRepository.save(value);
    }

    @Transactional
    public Value addNewValue(Value value, int metricId){
        value.setMetric(metricService.getMetricById(metricId));
        return valueRepository.save(value);
    }

    @Transactional
    public Value editValue(Integer id, String identifier, String moment, Double amount){
        Value value = valueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No value found on ID: " + id));

        value.setIdentifier(identifier);
        value.setIdentifier(identifier);
        value.setMoment(moment);
        value.setAmount(amount);
        
        return valueRepository.save(value);
    }

    @Transactional
    public void deleteValueById(Integer id){
        Value value = valueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No value found on ID: " + id));
        valueRepository.delete(value);
    }
    
}
