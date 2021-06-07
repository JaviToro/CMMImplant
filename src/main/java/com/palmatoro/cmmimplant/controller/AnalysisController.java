package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AnalysisService;

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
@RequestMapping(path = "/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Analysis> getAllAnalysis() {
        return analysisService.getAllAnalysiss();
    }

    @Secured("ROLE_USER")
    @Cacheable("analysis")
    @GetMapping("/{id}")
    public @ResponseBody
    Analysis getAnalysisById(@PathVariable Integer id) throws ResourceNotFoundException {
        return analysisService.getAnalysisById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Analysis addNewAnalysis(@RequestBody Analysis analysis) {
        return analysisService.addNewAnalysis(analysis);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteAnalysisById(@PathVariable Integer id) {
        analysisService.deleteAnalysisById(id);
    }
}
