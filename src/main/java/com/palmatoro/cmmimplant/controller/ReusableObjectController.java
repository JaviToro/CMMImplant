package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.ReusableObject;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ReusableObjectService;

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
@RequestMapping(path = "/reusableObject")
public class ReusableObjectController {
    @Autowired
    private ReusableObjectService reusableObjectService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ReusableObject> getAllReusableObject() {
        return reusableObjectService.getAllReusableObjects();
    }

    @Secured("ROLE_USER")
    @Cacheable("reusableObject")
    @GetMapping("/{id}")
    public @ResponseBody
    ReusableObject getReusableObjectById(@PathVariable Integer id) throws ResourceNotFoundException {
        return reusableObjectService.getReusableObjectById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    ReusableObject addNewReusableObject(@RequestBody ReusableObject reusableObject) {
        return reusableObjectService.addNewReusableObject(reusableObject);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteReusableObjectById(@PathVariable Integer id) {
        reusableObjectService.deleteReusableObjectById(id);
    }
}