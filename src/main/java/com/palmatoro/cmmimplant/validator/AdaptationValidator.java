package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Adaptation;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.AdaptationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AdaptationValidator implements Validator {

    @Autowired
    private AdaptationService adaptationService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Adaptation result = (Adaptation) o;

        for(Adaptation a: adaptationService.getAllAdaptations()){
            if(a.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
