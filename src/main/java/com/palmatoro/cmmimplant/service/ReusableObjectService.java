package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.ReusableObject;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.ReusableObjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReusableObjectService {

    private ReusableObjectRepository reusableObjectRepository;

    // Auxiliary Services --------------------------------------------
    @Autowired
    ProjectService projectService;

    public ReusableObjectService(ReusableObjectRepository reusableObjectRepository){
        this.reusableObjectRepository = reusableObjectRepository;
    }

    public Iterable<ReusableObject> getAllReusableObjects(){
        return reusableObjectRepository.findAll();
    }

    public ReusableObject getReusableObjectById(Integer id){
        ReusableObject reusableObject = reusableObjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No reusableObject found on ID: " + id));
        return reusableObject;
    }

    @Transactional
    public ReusableObject addNewReusableObject(ReusableObject reusableObject){
        reusableObject.setProject(projectService.getProjectByPrincipal());
        return reusableObjectRepository.save(reusableObject);
    }

    @Transactional
    public ReusableObject editReusableObject(Integer id, String identifier, String title, String description, String objectType, Date receptionDate, String link, String observations){
        ReusableObject reusableObject = reusableObjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No reusableObject found on ID: " + id));

        reusableObject.setIdentifier(identifier);
        reusableObject.setTitle(title);
        reusableObject.setDescription(description);
        reusableObject.setObjectType(objectType);
        reusableObject.setReceptionDate(receptionDate);
        reusableObject.setLink(link);
        reusableObject.setObservations(observations);

        return reusableObjectRepository.save(reusableObject);
    }

    @Transactional
    public void deleteReusableObjectById(Integer id){
        ReusableObject reusableObject = reusableObjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No reusableObject found on ID: " + id));
        reusableObjectRepository.delete(reusableObject);
    }
    
}
