package com.palmatoro.cmmimplant.controller;


import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Improvement;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ImprovementService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.ImprovementValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping(path = "/improvement")
public class ImprovementController {
    @Autowired
    private ImprovementService improvementService;

    @Autowired
    private ImprovementValidator improvementValidator;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/all", "/all/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Improvement> results = new ArrayList<Improvement>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            results = (List<Improvement>) improvementService.getAllImprovements();
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getImprovements();
        }

        model.addAttribute("results", results);

        return "improvement/list";
    }

    @Secured("ROLE_USER")
    @Cacheable("improvement")
    @GetMapping("/{id}")
    public @ResponseBody
    Improvement getImprovementById(@PathVariable Integer id) throws ResourceNotFoundException {
        return improvementService.getImprovementById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", improvementService.getImprovementById(id));
        } else {
            model.addAttribute("result", new Improvement());
        }

        return "improvement/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") Improvement result, BindingResult bindingResult) {
        improvementValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "improvement/add";
        }

        if (result.getId() != null) {
            improvementService.editImprovement(result.getId(), result.getTitle(), result.getDescription(), result.getStatus(), result.getImpact(),
             result.getPercentage(), result.getEstimatedEffort(), result.getRealEffort(), result.getReceptionDate(), result.getApprovalDate(), result.getEstimatedImplementation(),
              result.getRealImplementation(), result.getEvaluationDate(), result.getScore(), result.getObservations());
        } else {
            improvementService.addNewImprovement(result);
        }

        return "redirect:/improvement/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            improvementService.deleteImprovementById(id);
        } catch (Exception e) {
            return "redirect:/improvement/list/error/1";
        }

        return "redirect:/improvement/list";

    }
}
