package com.palmatoro.cmmimplant.controller;


import com.palmatoro.cmmimplant.domain.Alert;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AlertService;

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
@RequestMapping(path = "/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @Secured("ROLE_USER")
    @Cacheable("alert")
    @GetMapping("/{id}")
    public @ResponseBody
    Alert getAlertById(@PathVariable Integer id) throws ResourceNotFoundException {
        return alertService.getAlertById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Alert addNewAlert(@RequestBody Alert alert) {
        return alertService.addNewAlert(alert);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteAlertById(@PathVariable Integer id) {
        alertService.deleteAlertById(id);
    }
}
