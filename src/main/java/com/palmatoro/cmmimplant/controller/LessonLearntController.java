package com.palmatoro.cmmimplant.controller;

import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.LessonLearnt;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.LessonLearntService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.LessonLearntValidator;

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
@RequestMapping(path = "/lessonLearnt")
public class LessonLearntController {

    @Autowired
    private LessonLearntService lessonLearntService;

    @Autowired
    private LessonLearntValidator lessonLearntValidator;

    @Autowired
    private UserService userService;


    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }

        List<LessonLearnt> results = new ArrayList<LessonLearnt>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        
        
        if(isAdmin==true){
            lessonLearntService.getAllLessonLearnts().forEach(results::add);
        }else{
            results = userService.getUserByUsername(authentication.getName()).getProject().getLessonsLearnt();
        }

        model.addAttribute("results", results);

        return "lessonLearnt/list";
    }

    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public @ResponseBody
    LessonLearnt getLessonLearntById(@PathVariable Integer id) throws ResourceNotFoundException {
        return lessonLearntService.getLessonLearntById(id);
    }

    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)    
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("result", lessonLearntService.getLessonLearntById(id));
        } else {
            model.addAttribute("result", new LessonLearnt());
        }

        return "lessonLearnt/add";
    }

    @PostMapping("/add")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String addNew(@ModelAttribute("projectForm") LessonLearnt result, BindingResult bindingResult) {
        lessonLearntValidator.validate(result, bindingResult);

        if (bindingResult.hasErrors()) {
            return "lessonLearnt/add";
        }

        if (result.getId() != null) {
            lessonLearntService.editLessonLearnt(result.getId(), result.getIdentifier(), result.getTitle(), result.getDescription(),
             result.getReceptionDate(), result.getCommunicationDate(), result.getLink(), result.getObservations());
        } else {
            lessonLearntService.addNewLessonLearnt(result);
        }

        return "redirect:/lessonLearnt/list";
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_USER", "ROLE_PM", "ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            lessonLearntService.deleteLessonLearntById(id);
        } catch (Exception e) {
            return "redirect:/lessonLearnt/list/error/1";
        }

        return "redirect:/lessonLearnt/list";

    }
}
