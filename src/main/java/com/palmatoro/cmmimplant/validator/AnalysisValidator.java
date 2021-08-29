package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.AnalysisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        for(Analysis a: analysisService.getAllAnalysiss()){
            if(a.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
            if(a.getAnalysisIdentifier().equals(result.getAnalysisIdentifier()) && result.getId()!=null){
                errors.rejectValue("analysisIdentifier", "DuplicatedIdentifier");
            }
        }

        if(result.getEvaluationDate().before(result.getDate())){
            errors.rejectValue("evaluationDate", "CloseDateBefore");
        }
    }
}
