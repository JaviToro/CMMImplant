package com.palmatoro.cmmimplant.controller;

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!(user.getEmail().contains("@"))) {
            errors.rejectValue("email", "El email debe contener un @.");
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "El email indicado ya está registrado.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }

}
