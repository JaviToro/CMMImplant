package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Alert;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.AlertRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlertService {

    private AlertRepository alertRepository;

    // Auxiliary Services --------------------------------------------
    @Autowired
    ProjectService projectService;
    

    public AlertService(AlertRepository alertRepository){
        this.alertRepository = alertRepository;
    }

    public Iterable<Alert> getAllAlerts(){
        return alertRepository.findAll();
    }

    public Alert getAlertById(Integer id){
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No alert found on ID: " + id));
        return alert;
    }

    @Transactional
    public Alert addNewAlert(Alert alert){
        alert.setProject(projectService.getProjectByPrincipal());
        return alertRepository.save(alert);
    }

    @Transactional
    public Alert editAlert(Integer id, String identifier, String area, Date date){
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No alert found on ID: " + id));

        alert.setIdentifier(identifier);
        alert.setArea(area);
        alert.setDate(date);

        return alertRepository.save(alert);
    }

    @Transactional
    public void deleteAlertById(Integer id){
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No alert found on ID: " + id));
        alertRepository.delete(alert);
    }
    
}
