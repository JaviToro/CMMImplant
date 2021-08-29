package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Document;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        for(Document m: documentService.getAllDocuments()){
            if(m.getIdentifier().equals(result.getIdentifier()) && result.getId()!=null){
                errors.rejectValue("identifier", "DuplicatedIdentifier");
            }
        }
    }
}
