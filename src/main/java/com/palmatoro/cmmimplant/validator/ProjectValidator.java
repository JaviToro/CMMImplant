package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator {

    @Autowired
    private ProjectService projectService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectType", "NotEmpty");
        if (projectService.getProjectByName(project.getName()) != null) {
            errors.rejectValue("name", "DuplicatedProjectName");
        }

        if(project.getEndDate().before(project.getStartDate())){
            errors.rejectValue("endDate", "CloseDateBefore");
        }
    }
}
