package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Value;
import com.palmatoro.cmmimplant.service.MetricService;
import com.palmatoro.cmmimplant.service.ValueService;
import com.palmatoro.cmmimplant.validator.ValueValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/value")
public class ValueController {

    @Autowired
    private ValueService valueService;

    @Autowired
    private ValueValidator valueValidator;

    // @Autowired
    // private UserService userService;

    @Autowired
    private MetricService metricService;

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public String getValueById(Model model, @PathVariable(value = "id") Integer id) {

        model.addAttribute("result", valueService.getValueById(id));

        return "value/view";
    }

    @RequestMapping(value = {"/add/metric/{metricId}", "/add/{id}"}, method = RequestMethod.GET)
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "metricId", required = false) Integer metricId, @PathVariable(value = "id", required = false) Integer id) {

        //List<Metric> metrics = new ArrayList<>();

        if (id != null) {
            model.addAttribute("result", valueService.getValueById(id));
        } else {
            Value result = new Value();
            if (metricId != null) {
                result.setMetric(metricService.getMetricById(metricId));
            }
            model.addAttribute("result", result);
        }
        return "value/add";
    }

    @RequestMapping(value = {"/add", "/add/{metricId}", "/add/{id}"}, method = RequestMethod.POST)
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("result") Value result, BindingResult bindingResult,
                         @PathVariable(value = "metricId", required = false) Integer metricId, @PathVariable(value = "id", required = false) Integer id) {
        valueValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "value/add";
        }

        if (result.getId() != null) {
            valueService.editValue(result.getId(), result.getIdentifier(), result.getMoment(), result.getAmount());
        } else {
            valueService.addNewValue(result);
        }

        return "redirect:/metric/"+result.getMetric().getId().toString();
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        Integer metricId = valueService.getValueById(id).getMetric().getId();

        try {
            valueService.deleteValueById(id);
        } catch (Exception e) {
            return "redirect:/value/list/error/1";
        }

        return "redirect:/metric/"+metricId;

    }
}