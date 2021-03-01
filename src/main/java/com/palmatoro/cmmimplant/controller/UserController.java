package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    User addNewUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}