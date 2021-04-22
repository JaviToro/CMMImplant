package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.repository.ValueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/value")
public class ValueController {
    @Autowired
    private ValueRepository valueRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Value> getAllValues() {
        return valueRepository.findAll();
    }
}
