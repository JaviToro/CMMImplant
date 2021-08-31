package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.MetricService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.MetricValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/metric")
public class MetricController {

    @Autowired
    private MetricService metricService;

    @Autowired
    private MetricValidator metricValidator;

    @Autowired
    private UserService userService;


    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Metric> results = new ArrayList<Metric>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            metricService.getAllMetrics().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getMetrics();
        }

        model.addAttribute("results", results);

        return "metric/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public String getMetricById(Model model, @PathVariable(value = "id") Integer id) {

        model.addAttribute("result", metricService.getMetricById(id));
        model.addAttribute("values", metricService.getMetricById(id).getValues());

        return "metric/view";
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", metricService.getMetricById(id));
        } else {
            model.addAttribute("result", new Metric());
        }

        return "metric/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("result") Metric result, BindingResult bindingResult) {
        metricValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "metric/add";
        }

        if (result.getId() != null) {
            metricService.editMetric(result.getId(), result.getIdentifier(), result.getName(), result.getFormula(), result.getPeriod(),
             result.getUpperLimit(), result.getLowerLimit(), result.getObservations());
        } else {
            metricService.addNewMetric(result);
        }

        return "redirect:/metric/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            metricService.deleteMetricById(id);
        } catch (Exception e) {
            return "redirect:/metric/list/error/1";
        }

        return "redirect:/metric/list";

    }
}