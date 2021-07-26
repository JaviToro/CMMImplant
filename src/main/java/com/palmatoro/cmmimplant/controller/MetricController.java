package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.MetricService;

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
@RequestMapping(path = "/metric")
public class MetricController {
    @Autowired
    private MetricService metricService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Metric> getAllMetric() {
        return metricService.getAllMetrics();
    }

    @Secured("ROLE_USER")
    @Cacheable("metric")
    @GetMapping("/{id}")
    public @ResponseBody
    Metric getMetricById(@PathVariable Integer id) throws ResourceNotFoundException {
        return metricService.getMetricById(id);
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/add")
    public @ResponseBody
    Metric addNewMetric(@RequestBody Metric metric) {
        return metricService.addNewMetric(metric);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteMetricById(@PathVariable Integer id) {
        metricService.deleteMetricById(id);
    }
}