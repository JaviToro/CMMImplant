package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Impact;
import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.domain.RiskAndOpportunity.RiskCategory;
import com.palmatoro.cmmimplant.domain.RiskAndOpportunity.RiskMonitorization;
import com.palmatoro.cmmimplant.domain.RiskAndOpportunity.RiskProbability;
import com.palmatoro.cmmimplant.domain.RiskAndOpportunity.RiskType;
import com.palmatoro.cmmimplant.domain.Status;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.RiskAndOpportunityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RiskAndOpportunityService {

    private RiskAndOpportunityRepository riskAndOpportunityRepository;

    // Auxiliary Services --------------------------------------------
    @Autowired
    ProjectService projectService;

    public RiskAndOpportunityService(RiskAndOpportunityRepository riskAndOpportunityRepository){
        this.riskAndOpportunityRepository = riskAndOpportunityRepository;
    }

    public Iterable<RiskAndOpportunity> getAllRiskAndOpportunitys(){
        return riskAndOpportunityRepository.findAll();
    }

    public RiskAndOpportunity getRiskAndOpportunityById(Integer id){
        RiskAndOpportunity riskAndOpportunity = riskAndOpportunityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No riskAndOpportunity found on ID: " + id));
        return riskAndOpportunity;
    }

    @Transactional
    public RiskAndOpportunity addNewRiskAndOpportunity(RiskAndOpportunity riskAndOpportunity){
        riskAndOpportunity.setProject(projectService.getProjectByPrincipal());
        riskAndOpportunity.setPriority();
        return riskAndOpportunityRepository.save(riskAndOpportunity);
    }

    @Transactional
    public RiskAndOpportunity editRiskAndOpportunity(Integer id, String identifier, RiskType type, RiskCategory category, String title, String description, Date identificationDate, RiskProbability probability, 
    Impact impact, String threshold, String consequences, String actionPlan, Status status, RiskMonitorization monitorization, Date lastRevisionDate, Date closeDate, String observations){
        RiskAndOpportunity riskAndOpportunity = riskAndOpportunityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No riskAndOpportunity found on ID: " + id));

        riskAndOpportunity.setIdentifier(identifier);
        riskAndOpportunity.setType(type);
        riskAndOpportunity.setCategory(category);
        riskAndOpportunity.setTitle(title);
        riskAndOpportunity.setDescription(description);
        riskAndOpportunity.setIdentificationDate(identificationDate);
        riskAndOpportunity.setProbability(probability);
        riskAndOpportunity.setImpact(impact);
        riskAndOpportunity.setThreshold(threshold);
        riskAndOpportunity.setConsequences(consequences);
        riskAndOpportunity.setActionPlan(actionPlan);
        riskAndOpportunity.setStatus(status);
        riskAndOpportunity.setMonitorization(monitorization);
        riskAndOpportunity.setLastRevisionDate(lastRevisionDate);
        riskAndOpportunity.setCloseDate(closeDate);
        riskAndOpportunity.setObservations(observations);
        riskAndOpportunity.setPriority();

        return riskAndOpportunityRepository.save(riskAndOpportunity);
    }

    @Transactional
    public void deleteRiskAndOpportunityById(Integer id){
        RiskAndOpportunity riskAndOpportunity = riskAndOpportunityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No riskAndOpportunity found on ID: " + id));
        riskAndOpportunityRepository.delete(riskAndOpportunity);
    }
    
}
