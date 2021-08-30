package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Audit;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuditValidator implements Validator {

    @Autowired
    private AuditService auditService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Audit result = (Audit) o;

        for(Audit a: auditService.getAllAudits()){
            if(a.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
