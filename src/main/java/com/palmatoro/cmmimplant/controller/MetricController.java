package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.repository.MetricRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/metric")
public class MetricController {
    @Autowired
    private MetricRepository metricRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }
}
