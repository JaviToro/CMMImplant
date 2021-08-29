package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Alert;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AlertValidator implements Validator {

    @Autowired
    private AlertService alertService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Alert result = (Alert) o;

        for(Alert a: alertService.getAllAlerts()){
            if(a.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
