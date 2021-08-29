package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.ValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ValueValidator implements Validator {

    @Autowired
    private ValueService valueService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Value result = (Value) o;

        for(Value v: valueService.getAllValues()){
            if(v.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
