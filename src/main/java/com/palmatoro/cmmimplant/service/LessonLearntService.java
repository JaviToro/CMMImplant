package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.LessonLearnt;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.LessonLearntRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonLearntService {

    private LessonLearntRepository lessonLearntRepository;
    
    // Auxiliary Services --------------------------------------------
    @Autowired
    ProjectService projectService;

    public LessonLearntService(LessonLearntRepository lessonLearntRepository){
        this.lessonLearntRepository = lessonLearntRepository;
    }

    public Iterable<LessonLearnt> getAllLessonLearnts(){
        return lessonLearntRepository.findAll();
    }

    public LessonLearnt getLessonLearntById(Integer id){
        LessonLearnt lessonLearnt = lessonLearntRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No lessonLearnt found on ID: " + id));
        return lessonLearnt;
    }

    @Transactional
    public LessonLearnt addNewLessonLearnt(LessonLearnt lessonLearnt){
        lessonLearnt.setProject(projectService.getProjectByPrincipal());
        return lessonLearntRepository.save(lessonLearnt);
    }

    @Transactional
    public LessonLearnt editLessonLearnt(Integer id, String identifier, String title, String description, Date receptionDate, Date communicationDate, String link, String observations){
        LessonLearnt lessonLearnt = lessonLearntRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No lessonLearnt found on ID: " + id));

        lessonLearnt.setIdentifier(identifier);
        lessonLearnt.setTitle(title);
        lessonLearnt.setDescription(description);
        lessonLearnt.setReceptionDate(receptionDate);
        lessonLearnt.setCommunicationDate(communicationDate);
        lessonLearnt.setLink(link);
        lessonLearnt.setObservations(observations);

        return lessonLearntRepository.save(lessonLearnt);
    }

    @Transactional
    public void deleteLessonLearntById(Integer id){
        LessonLearnt lessonLearnt = lessonLearntRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No lessonLearnt found on ID: " + id));
        lessonLearntRepository.delete(lessonLearnt);
    }
    
}
