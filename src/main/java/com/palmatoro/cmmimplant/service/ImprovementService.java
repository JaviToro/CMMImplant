package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Impact;
import com.palmatoro.cmmimplant.domain.Improvement;
import com.palmatoro.cmmimplant.domain.Improvement.ImprovementStatus;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.ImprovementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImprovementService {

    private ImprovementRepository improvementRepository;

    // Auxiliary Services --------------------------------------------
    @Autowired
    ProjectService projectService;

    public ImprovementService(ImprovementRepository improvementRepository){
        this.improvementRepository = improvementRepository;
    }

    public Iterable<Improvement> getAllImprovements(){
        return improvementRepository.findAll();
    }

    public Improvement getImprovementById(Integer id){
        Improvement improvement = improvementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No improvement found on ID: " + id));
        return improvement;
    }

    @Transactional
    public Improvement addNewImprovement(Improvement improvement){
        improvement.setProject(projectService.getProjectByPrincipal());
        return improvementRepository.save(improvement);
    }

    @Transactional
    public Improvement editImprovement(Integer id, String title, String description, ImprovementStatus status, Impact impact, Double percentage, String estimatedEffort, String realEffort, 
    Date receptionDate, Date approvalDate, Date estimatedImplementation, Date realImplementation, Date evaluationDate, Double score, String observations, User responsable){
        Improvement improvement = improvementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No improvement found on ID: " + id));

        improvement.setTitle(title);
        improvement.setDescription(description);
        improvement.setStatus(status);
        improvement.setImpact(impact);
        improvement.setPercentage(percentage);
        improvement.setEstimatedEffort(estimatedEffort);
        improvement.setRealEffort(realEffort);
        improvement.setReceptionDate(receptionDate);
        improvement.setApprovalDate(approvalDate);
        improvement.setEstimatedImplementation(estimatedImplementation);
        improvement.setRealImplementation(realImplementation);
        improvement.setEvaluationDate(evaluationDate);
        improvement.setScore(score);
        improvement.setObservations(observations);
        improvement.setResponsable(responsable);

        return improvementRepository.save(improvement);
    }

    @Transactional
    public void deleteImprovementById(Integer id){
        Improvement improvement = improvementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No improvement found on ID: " + id));
        improvementRepository.delete(improvement);
    }
    
}
