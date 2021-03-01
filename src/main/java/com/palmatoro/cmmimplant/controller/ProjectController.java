package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
