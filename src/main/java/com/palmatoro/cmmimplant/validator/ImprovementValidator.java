package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Improvement;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.ImprovementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ImprovementValidator implements Validator {

    @Autowired
    private ImprovementService improvementService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Improvement result = (Improvement) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "impact", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "percentage", "NotEmpty");

        for(Improvement i: improvementService.getAllImprovements()){
            if(i.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
        if(result.getEvaluationDate()!=null && result.getRealImplementation()!=null){
            if(result.getEvaluationDate().before(result.getRealImplementation())){
                errors.rejectValue("evaluationDate", "CloseDateBefore");
            }
        }
        
        if(result.getRealImplementation()!=null && result.getApprovalDate()!=null){
            if(result.getRealImplementation().before(result.getApprovalDate())){
                errors.rejectValue("realImplementation", "CloseDateBefore");
            }
        }
        if(result.getRealImplementation()!=null && result.getReceptionDate()!=null){
            if(result.getRealImplementation().before(result.getReceptionDate())){
                errors.rejectValue("realImplementation", "CloseDateBefore");
            }
        }
    }
}
