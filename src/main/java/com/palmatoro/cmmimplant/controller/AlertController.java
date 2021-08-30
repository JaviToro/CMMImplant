package com.palmatoro.cmmimplant.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Alert;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AlertService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.AlertValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlertValidator alertValidator;

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Alert> results = new ArrayList<Alert>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            alertService.getAllAlerts().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getAlerts();
        }

        model.addAttribute("results", results);

        return "alert/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    Alert getAlertById(@PathVariable Integer id) throws ResourceNotFoundException {
        return alertService.getAlertById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", alertService.getAlertById(id));
        } else {
            model.addAttribute("result", new Alert());
        }

        return "alert/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("result") Alert result, BindingResult bindingResult) {
        alertValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "alert/add";
        }

        if (result.getId() != null) {
            alertService.editAlert(result.getId(), result.getIdentifier(), result.getArea(), result.getDate());
        } else {
            alertService.addNewAlert(result);
        }

        return "redirect:/alert/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            alertService.deleteAlertById(id);
        } catch (Exception e) {
            return "redirect:/alert/list/error/1";
        }

        return "redirect:/alert/list";

    }
}