package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Secured("ROLE_PM")
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Secured("ROLE_PM")
    @Cacheable("project")
    @GetMapping("/{id}")
    public @ResponseBody
    Project getProjectById(@PathVariable Integer id) throws ResourceNotFoundException {
        return projectService.getProjectById(id);
    }

    @Secured("ROLE_PM")
    @PostMapping(path = "/add")
    public @ResponseBody
    Project addNewProject(@RequestBody Project project) {
        return projectService.addNewProject(project);
    }

    @Secured("ROLE_PM")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteProjectById(@PathVariable Integer id) {
        projectService.deleteProjectById(id);
    }
}
