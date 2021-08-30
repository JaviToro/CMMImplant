package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.ReusableObject;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.ReusableObjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReusableObjectValidator implements Validator {

    @Autowired
    private ReusableObjectService reusableObjectService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ReusableObject result = (ReusableObject) o;

        for(ReusableObject ro: reusableObjectService.getAllReusableObjects()){
            if(ro.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
