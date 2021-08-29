package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AnalysisService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.AnalysisValidator;

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
@RequestMapping(path = "/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private AnalysisValidator analysisValidator;

    @Autowired
    private UserService userService;


    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Analysis> results = new ArrayList<Analysis>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            analysisService.getAllAnalysiss().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getAnalysis();
        }

        model.addAttribute("results", results);

        return "analysis/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    Analysis getAnalysisById(@PathVariable Integer id) throws ResourceNotFoundException {
        return analysisService.getAnalysisById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        User principal = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<User> pms = this.userService.getAllPM(principal.getProject().getId());

        if (id != null) {
            model.addAttribute("result", analysisService.getAnalysisById(id));
            model.addAttribute("pms", pms);
        } else {
            model.addAttribute("result", new Analysis());
            model.addAttribute("pms", pms);
        }

        return "analysis/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") Analysis result, BindingResult bindingResult) {
        analysisValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "analysis/add";
        }

        if (result.getId() != null) {
            analysisService.editAnalysis(result.getId(), result.getIdentifier(), result.getType(), result.getAnalysisIdentifier(), result.getStatus(),
             result.getDirection(), result.getDate(), result.getEvaluationDate(), result.getObservations());
        } else {
            analysisService.addNewAnalysis(result);
        }

        return "redirect:/analysis/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            analysisService.deleteAnalysisById(id);
        } catch (Exception e) {
            return "redirect:/analysis/list/error/1";
        }

        return "redirect:/analysis/list";

    }
}

