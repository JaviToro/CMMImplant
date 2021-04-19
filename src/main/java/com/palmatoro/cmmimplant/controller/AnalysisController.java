package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.repository.AnalysisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/analysis")
public class AnalysisController {
    @Autowired
    private AnalysisRepository analysisRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Analysis> getAllAnalysis() {
        return analysisRepository.findAll();
    }
}
