package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.repository.RiskAndOpportunityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/risk")
public class RiskAndOpportunityController {
    @Autowired
    private RiskAndOpportunityRepository riskAndOpportunityRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<RiskAndOpportunity> getAllRisks() {
        return riskAndOpportunityRepository.findAll();
    }
}
