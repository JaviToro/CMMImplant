package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.RiskAndOpportunity;
import com.palmatoro.cmmimplant.service.RiskAndOpportunityService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.RiskAndOpportunityValidator;

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

@Controller
@RequestMapping(path = "/riskAndOpportunity")
public class RiskAndOpportunityController {

    @Autowired
    private RiskAndOpportunityService riskAndOpportunityService;

    @Autowired
    private RiskAndOpportunityValidator riskAndOpportunityValidator;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/all", "/all/error/{code}"}, method = RequestMethod.GET)
    public String allRisks(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<RiskAndOpportunity> results = new ArrayList<RiskAndOpportunity>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            results = (List<RiskAndOpportunity>) riskAndOpportunityService.getAllRiskAndOpportunitys();
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getRisksAndOpportunities();
        }

        model.addAttribute("results", results);

        return "riskAndOpportunity/list";
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String getResultById(Model model, @PathVariable(value = "id") Integer id) {

        model.addAttribute("result", riskAndOpportunityService.getRiskAndOpportunityById(id));

        return "riskAndOpportunity/view";
    }
    
    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", riskAndOpportunityService.getRiskAndOpportunityById(id));
        } else {
            model.addAttribute("result", new RiskAndOpportunity());
        }

        return "riskAndOpportunity/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") RiskAndOpportunity result, BindingResult bindingResult) {
        riskAndOpportunityValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "riskAndOpportunity/add";
        }

        if (result.getId() != null) {
            riskAndOpportunityService.editRiskAndOpportunity(result.getId(), result.getIdentifier(), result.getType(), result.getCategory(), result.getTitle(),
             result.getDescription(), result.getIdentificationDate(), result.getProbability(), result.getImpact(), result.getThreshold(), result.getConsequences(),
             result.getActionPlan(), result.getStatus(), result.getMonitorization(), result.getLastRevisionDate(), result.getCloseDate(), result.getObservations(), result.getPriority());
        } else {
            riskAndOpportunityService.addNewRiskAndOpportunity(result);
        }

        return "redirect:/riskAndOpportunity/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            riskAndOpportunityService.deleteRiskAndOpportunityById(id);
        } catch (Exception e) {
            return "redirect:/riskAndOpportunity/list/error/1";
        }

        return "redirect:/riskAndOpportunity/list";

    }
}