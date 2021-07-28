package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ValueService;

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
@RequestMapping(path = "/value")
public class ValueController {
    @Autowired
    private ValueService valueService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Value> getAllValue() {
        return valueService.getAllValues();
    }

    @Secured("ROLE_USER")
    @Cacheable("value")
    @GetMapping("/{id}")
    public @ResponseBody
    Value getValueById(@PathVariable Integer id) throws ResourceNotFoundException {
        return valueService.getValueById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Value addNewValue(@RequestBody Value value, @PathVariable Integer metricId) {
        return valueService.addNewValue(value, metricId);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteValueById(@PathVariable Integer id) {
        valueService.deleteValueById(id);
    }
}
