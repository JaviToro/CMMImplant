package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.service.ProjectService;

import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/all", "/all/error/{code}"}, method = RequestMethod.GET)
    public String allProjects(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null)
            model.addAttribute("error", "No puedes eliminar el proyecto al que perteneces.");

        List<Project> projects = projectService.getAllProjectsAsList();

        model.addAttribute("project", new Project());
        model.addAttribute("projects", projects);

        return "project/projects";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public String getProjectById(Model model, @PathVariable(value = "id") Integer id) {

        model.addAttribute("project", projectService.getProjectById(id));

        return "project/project";
    }

    @Secured({"ROLE_USER", "ROLE_PM"})
    @GetMapping("/current")
    public String myProject(Model model) {

        model.addAttribute("project", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getProject());

        return "project/project";

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/add", "/add/{id}"}, method = RequestMethod.GET)
    public String addNewProject(Model model, @PathVariable(value = "id", required = false) Integer id) {

        if (id != null) {
            model.addAttribute("projectForm", projectService.getProjectById(id));
        } else {
            model.addAttribute("projectForm", new Project());
        }

        return "project/addProject";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String addNewProject(@ModelAttribute("projectForm") Project project, BindingResult bindingResult) {
        projectValidator.validate(project, bindingResult);

        if (bindingResult.hasErrors()) {
            return "project/addProject";
        }

        if (project.getId() != null) {
            projectService.editProject(project.getId(), project.getName(), project.getProjectType(), project.getStartDate(), project.getEndDate());
        } else {
            projectService.addNewProject(project);
        }

        return "redirect:/project/all";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete/{id}")
    public String deleteProjectById(@PathVariable(value = "id") Integer id) {

        try {
            projectService.deleteProjectById(id);
        } catch (Exception e) {
            return "redirect:/project/all/error/1";
        }

        return "redirect:/project/all";

    }

}
