package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.MetricService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MetricValidator implements Validator {

    @Autowired
    private MetricService metricService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Metric result = (Metric) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formula", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "period", "NotEmpty");

        for(Metric m: metricService.getAllMetrics()){
            if(m.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
