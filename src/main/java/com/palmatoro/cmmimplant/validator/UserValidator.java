package com.palmatoro.cmmimplant.validator;

import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acronym", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userRole", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "project", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!(user.getEmail().contains("@") && user.getEmail().contains("."))) {
            errors.rejectValue("email", "EmailFormat");
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "DuplicatedEmail");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }

}
