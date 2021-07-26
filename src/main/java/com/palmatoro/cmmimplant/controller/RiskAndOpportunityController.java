package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.RiskAndOpportunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/riskAndOpportunity")
public class RiskAndOpportunityController {
    @Autowired
    private RiskAndOpportunityService riskAndOpportunityService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<RiskAndOpportunity> getAllRiskAndOpportunity() {
        return riskAndOpportunityService.getAllRiskAndOpportunitys();
    }

    @Secured("ROLE_USER")
    @Cacheable("riskAndOpportunity")
    @GetMapping("/{id}")
    public @ResponseBody
    RiskAndOpportunity getRiskAndOpportunityById(@PathVariable Integer id) throws ResourceNotFoundException {
        return riskAndOpportunityService.getRiskAndOpportunityById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    RiskAndOpportunity addNewRiskAndOpportunity(@RequestBody RiskAndOpportunity riskAndOpportunity) {
        return riskAndOpportunityService.addNewRiskAndOpportunity(riskAndOpportunity);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteRiskAndOpportunityById(@PathVariable Integer id) {
        riskAndOpportunityService.deleteRiskAndOpportunityById(id);
    }
}