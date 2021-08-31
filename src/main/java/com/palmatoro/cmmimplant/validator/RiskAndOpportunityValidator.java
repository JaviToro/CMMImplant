package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.RiskAndOpportunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RiskAndOpportunityValidator implements Validator {

    @Autowired
    private RiskAndOpportunityService riskAndOpportunityService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RiskAndOpportunity result = (RiskAndOpportunity) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "type", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "category", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "identificationDate", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "threshold", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consequences", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actionPlan", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "status", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "monitorization", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "lastRevisionDate", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "impact", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "probability", "NotEmpty");

        for(RiskAndOpportunity ro: riskAndOpportunityService.getAllRiskAndOpportunitys()){
            if(ro.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
        if(result.getCloseDate()!=null && result.getIdentificationDate()!=null){
            if(result.getCloseDate().before(result.getIdentificationDate())){
            errors.rejectValue("closeDate", "CloseDateBefore");
            }
        }
        
    }
}
