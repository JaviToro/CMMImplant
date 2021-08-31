package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Document;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DocumentValidator implements Validator {

    @Autowired
    private DocumentService documentService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Document result = (Document) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direction", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "practiceAreas", "NotEmpty");

        for(Document d: documentService.getAllDocuments()){
            if(d.getIdentifier().equals(result.getIdentifier()) && result.getId()==null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
