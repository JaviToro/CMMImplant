package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Metric;
import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.service.ValueService;
import com.palmatoro.cmmimplant.validator.ValueValidator;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/value")
public class ValueController {

    @Autowired
    private ValueService valueService;

    @Autowired
    private ValueValidator valueValidator;

    @Autowired
    private UserService userService;


    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/metric/{id}", "/list/metric{id}/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "id", required = false) Integer metricId, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error.");
        }

        List<Value> results = new ArrayList<Value>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            results = (List<Value>) valueService.getAllValues();
        }else{
            Metric m = userService.getUserByUsername(authentication.getName()).getProject().getMetrics().get(metricId);
            results = m.getValues();
        }

        model.addAttribute("results", results);
        model.addAttribute("metricId", metricId);

        return "value/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    Value getValueById(@PathVariable Integer id) throws ResourceNotFoundException {
        return valueService.getValueById(id);
    }

    @RequestMapping(value = {"/add/{metricId}", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "metricId", required = false) Integer metricId, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", valueService.getValueById(id));
        } else {
            model.addAttribute("result", new Value());
            model.addAttribute("metricId", metricId);
        }

        return "value/add";
    }

    @RequestMapping(value = {"/add/{metricId}", "/add/{id}"}, method = RequestMethod.POST)  
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") Value result, BindingResult bindingResult,
    @PathVariable(value = "metricId", required = false) Integer metricId, @PathVariable(value = "id", required = false) Integer id) {
        valueValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "value/add";
        }

        if (result.getId() != null) {
            valueService.editValue(result.getId(), result.getIdentifier(), result.getMoment(), result.getAmount());
        } else {
            valueService.addNewValue(result, metricId);
        }

        return "redirect:/value/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            valueService.deleteValueById(id);
        } catch (Exception e) {
            return "redirect:/value/list/error/1";
        }

        return "redirect:/value/list";

    }
}