package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.LessonLearnt;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.LessonLearntService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LessonLearntValidator implements Validator {

    @Autowired
    private LessonLearntService lessonLearntService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LessonLearnt result = (LessonLearnt) o;

        for(LessonLearnt ll: lessonLearntService.getAllLessonLearnts()){
            if(ll.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
        if(result.getCommunicationDate()!=null && result.getReceptionDate()!=null){
            if(result.getCommunicationDate().before(result.getReceptionDate())){
                errors.rejectValue("communicationDate", "CommunicationDateBefore");
            }
        }
    }
}
