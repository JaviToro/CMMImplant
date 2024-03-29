package com.palmatoro.cmmimplant.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.palmatoro.cmmimplant.domain.ReusableObject;
import com.palmatoro.cmmimplant.service.ReusableObjectService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.ReusableObjectValidator;

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

@Controller
@RequestMapping(path = "/reusableObject")
public class ReusableObjectController {
    @Autowired
    private ReusableObjectService reusableObjectService;

    @Autowired
    private ReusableObjectValidator reusableObjectValidator;

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

        List<ReusableObject> results = new ArrayList<ReusableObject>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            reusableObjectService.getAllReusableObjects().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getReusableObjects();
        }

        model.addAttribute("results", results);

        return "reusableObject/list";
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String getResultById(Model model, @PathVariable(value = "id") Integer id) {

        model.addAttribute("result", reusableObjectService.getReusableObjectById(id));

        return "reusableObject/view";
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", reusableObjectService.getReusableObjectById(id));
        } else {
            model.addAttribute("result", new ReusableObject());
        }

        return "reusableObject/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("result") ReusableObject result, BindingResult bindingResult) {
        reusableObjectValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "reusableObject/add";
        }

        if (result.getId() != null) {
            reusableObjectService.editReusableObject(result.getId(), result.getIdentifier(), result.getTitle(), result.getDescription(),
             result.getObjectType(), result.getReceptionDate(), result.getLink(), result.getObservations());
        } else {
            reusableObjectService.addNewReusableObject(result);
        }

        return "redirect:/reusableObject/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            reusableObjectService.deleteReusableObjectById(id);
        } catch (Exception e) {
            return "redirect:/reusableObject/list/error/1";
        }

        return "redirect:/reusableObject/list";

    }
}