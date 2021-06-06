package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Adaptation;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AdaptationService;

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
@RequestMapping(path = "/adaptation")
public class AdaptationController {

    @Autowired
    private AdaptationService adaptationService;

    @Secured("ROLE_USER")
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Adaptation> getAllAdaptations() {
        return adaptationService.getAllAdaptations();
    }

    @Secured("ROLE_USER")
    @Cacheable("adaptation")
    @GetMapping("/{id}")
    public @ResponseBody
    Adaptation getAdaptationById(@PathVariable Integer id) throws ResourceNotFoundException {
        return adaptationService.getAdaptationById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Adaptation addNewAdaptation(@RequestBody Adaptation adaptation) {
        return adaptationService.addNewAdaptation(adaptation);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteAdaptationById(@PathVariable Integer id) {
        adaptationService.deleteAdaptationById(id);
    }
}
