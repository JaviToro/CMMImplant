package com.palmatoro.cmmimplant.controller;


import com.palmatoro.cmmimplant.domain.Improvement;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ImprovementService;

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
@RequestMapping(path = "/improvement")
public class ImprovementController {
    @Autowired
    private ImprovementService improvementService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Improvement> getAllImprovement() {
        return improvementService.getAllImprovements();
    }

    @Secured("ROLE_USER")
    @Cacheable("improvement")
    @GetMapping("/{id}")
    public @ResponseBody
    Improvement getImprovementById(@PathVariable Integer id) throws ResourceNotFoundException {
        return improvementService.getImprovementById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Improvement addNewImprovement(@RequestBody Improvement improvement) {
        return improvementService.addNewImprovement(improvement);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteImprovementById(@PathVariable Integer id) {
        improvementService.deleteImprovementById(id);
    }
