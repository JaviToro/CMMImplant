package com.palmatoro.cmmimplant.Project;

import com.palmatoro.cmmimplant.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Project> getAllUsers() {
        return projectRepository.findAll();
    }
}
