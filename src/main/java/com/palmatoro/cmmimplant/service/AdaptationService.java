package com.palmatoro.cmmimplant.service;

import com.palmatoro.cmmimplant.domain.Adaptation;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.AdaptationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdaptationService {

    private AdaptationRepository adaptationRepository;

    public AdaptationService(AdaptationRepository adaptationRepository){
        this.adaptationRepository = adaptationRepository;
    }

    public Iterable<Adaptation> getAllAdaptations(){
        return adaptationRepository.findAll();
    }

    public Adaptation getAdaptationById(Integer id){
        Adaptation adaptation = adaptationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No adaptation found on ID: " + id));
        return adaptation;
    }

    @Transactional
    public Adaptation addNewAdaptation(Adaptation adaptation){
        return adaptationRepository.save(adaptation);
    }

    @Transactional
    public Adaptation editAdaptation(Integer id, String identifier, String practiceArea, String arrangement, String observations){
        Adaptation adaptation = adaptationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No adaptation found on ID: " + id));

        adaptation.setIdentifier(identifier);
        adaptation.setPracticeArea(practiceArea);
        adaptation.setArrangement(arrangement);
        adaptation.setObservations(observations);

        return adaptationRepository.save(adaptation);
    }

    @Transactional
    public void deleteAdaptationById(Integer id){
        Adaptation adaptation = adaptationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No adaptation found on ID: " + id));
        adaptationRepository.delete(adaptation);
    }
    
}
