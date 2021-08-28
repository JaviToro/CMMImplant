package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ProjectService;

import com.palmatoro.cmmimplant.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectValidator projectValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/all")
    public String allProjects(Model model) {
       List<Project> projects = projectService.getAllProjectsAsList();

       model.addAttribute("project", new Project());
       model.addAttribute("projects", projects);

       return "projects";
    }

    @Secured("ROLE_PM")
    @Cacheable("project")
    @GetMapping("/{id}")
    public @ResponseBody
    Project getProjectById(@PathVariable Integer id) throws ResourceNotFoundException {
        return projectService.getProjectById(id);
    }

    @Secured("ROLE_ANONYMOUS")
    @GetMapping(path = "/add")
    public String addNewProject(Model model) {

        model.addAttribute("projectForm", new Project());

        return "addProject";
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/add")
    public String addNewProject(@ModelAttribute("projectForm") Project project, BindingResult bindingResult) {
        projectValidator.validate(project, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addProject";
        }

        projectService.addNewProject(project);

        return "redirect:/project/all";
    }

    @Secured("ROLE_PM")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteProjectById(@PathVariable Integer id) {
        projectService.deleteProjectById(id);
    }
}
