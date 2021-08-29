package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Adaptation;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AdaptationService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.AdaptationValidator;

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
@RequestMapping(path = "/adaptation")
public class AdaptationController {

    @Autowired
    private AdaptationService adaptationService;

    @Autowired
    private AdaptationValidator adaptationValidator;

    @Autowired
    private UserService userService;


    @GetMapping(path = "/list")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/all", "/all/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Adaptation> results = new ArrayList<Adaptation>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            results = (List<Adaptation>) adaptationService.getAllAdaptations();
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getAdaptations();
        }

        model.addAttribute("results", results);

        return "adaptation/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    Adaptation getAdaptationById(@PathVariable Integer id) throws ResourceNotFoundException {
        return adaptationService.getAdaptationById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", adaptationService.getAdaptationById(id));
        } else {
            model.addAttribute("result", new Adaptation());
        }

        return "adaptation/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") Adaptation result, BindingResult bindingResult) {
        adaptationValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "adaptation/add";
        }

        if (result.getId() != null) {
            adaptationService.editAdaptation(result.getId(), result.getIdentifier(), result.getPracticeArea(), result.getArrangement(), result.getObservations());
        } else {
            adaptationService.addNewAdaptation(result);
        }

        return "redirect:/adaptation/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            adaptationService.deleteAdaptationById(id);
        } catch (Exception e) {
            return "redirect:/adaptation/list/error/1";
        }

        return "redirect:/adaptation/list";

    }
}