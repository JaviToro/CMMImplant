package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.RiskAndOpportunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        for(RiskAndOpportunity ro: riskAndOpportunityService.getAllRiskAndOpportunitys()){
            if(ro.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
        if(result.getCloseDate().before(result.getIdentificationDate())){
            errors.rejectValue("closeDate", "CloseDateBefore");
        }
    }
}
