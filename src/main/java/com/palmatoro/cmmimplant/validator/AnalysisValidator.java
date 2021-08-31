package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.AnalysisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AnalysisValidator implements Validator {

    @Autowired
    private AnalysisService analysisService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Analysis result = (Analysis) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "type", "NotEmpty");        
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "analysisIdentifier", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "status", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direction", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "date", "NotEmpty");

        for(Analysis a: analysisService.getAllAnalysiss()){
            if(a.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }

        if(result.getEvaluationDate()!=null && result.getDate()!=null){
            if(result.getEvaluationDate().before(result.getDate())){
                errors.rejectValue("evaluationDate", "CloseDateBefore");
            }
        }
    }
}
