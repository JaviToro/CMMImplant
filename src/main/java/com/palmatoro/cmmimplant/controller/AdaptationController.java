package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Adaptation;
import com.palmatoro.cmmimplant.repository.AdaptationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/adaptation")
public class AdaptationController {
    @Autowired
    private AdaptationRepository adaptationRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Adaptation> getAllAdaptations() {
        return adaptationRepository.findAll();
    }
}
