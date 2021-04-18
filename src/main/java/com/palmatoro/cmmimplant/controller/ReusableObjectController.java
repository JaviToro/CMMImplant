package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.ReusableObject;
import com.palmatoro.cmmimplant.repository.ReusableObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/object")
public class ReusableObjectController {
    @Autowired
    private ReusableObjectRepository reusableObjectRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ReusableObject> getAllObjects() {
        return reusableObjectRepository.findAll();
    }
}
