package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Audit;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.AuditService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.AuditValidator;

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
@RequestMapping(path = "/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private AuditValidator auditValidator;

    @Autowired
    private UserService userService;


    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<Audit> results = new ArrayList<Audit>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            auditService.getAllAudits().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getAudits();
        }

        model.addAttribute("results", results);

        return "audit/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    Audit getAuditById(@PathVariable Integer id) throws ResourceNotFoundException {
        return auditService.getAuditById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        User principal = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<User> pms = this.userService.getAllPM(principal.getProject().getId());
        List<User> users = this.userService.getAllUsersByProjectId(principal.getProject().getId());

        if (id != null) {
            model.addAttribute("result", auditService.getAuditById(id));
            model.addAttribute("pms", pms);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("result", new Audit());
            model.addAttribute("pms", pms);
            model.addAttribute("users", users);
        }

        return "audit/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") Audit result, BindingResult bindingResult) {
        auditValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "audit/add";
        }

        if (result.getId() != null) {
            auditService.editAudit(result.getId(), result.getIdentifier(), result.getType(), result.getAuditIdentifier(),
             result.getAuditDate(), result.getStatus(), result.getInitialErrors(), result.getActualErrors(), result.getDirection(), result.getObservations());
        } else {
            auditService.addNewAudit(result);
        }

        return "redirect:/audit/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            auditService.deleteAuditById(id);
        } catch (Exception e) {
            return "redirect:/audit/list/error/1";
        }

        return "redirect:/audit/list";

    }
}
