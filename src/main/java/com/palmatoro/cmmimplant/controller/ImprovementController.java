package com.palmatoro.cmmimplant.controller;


import com.palmatoro.cmmimplant.domain.Improvement;
import com.palmatoro.cmmimplant.repository.ImprovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/improvement")
public class ImprovementController {
    @Autowired
    private ImprovementRepository improvementRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Improvement> getAllImprovements() {
        return improvementRepository.findAll();
    }
}
