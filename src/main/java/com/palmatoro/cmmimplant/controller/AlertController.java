package com.palmatoro.cmmimplant.controller;


import com.palmatoro.cmmimplant.domain.Alert;
import com.palmatoro.cmmimplant.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/faq")
public class AlertController {
    @Autowired
    private AlertRepository alertRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
