package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @Secured("ROLE_RP")
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Secured("ROLE_RP")
    @Cacheable("user")
    @GetMapping("/{id}")
    public @ResponseBody
    User getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
        return userService.getUserById(id);
    }

    @Secured("ROLE_RP")
    @PostMapping(path = "/add")
    public @ResponseBody
    User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @Secured("ROLE_RP")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

}