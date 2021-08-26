package com.palmatoro.cmmimplant.controller;

import java.security.Principal;

import java.util.List;

import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.SecurityService;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.service.ProjectService;

import com.palmatoro.cmmimplant.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @Secured("ROLE_PM")
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Secured("ROLE_PM")
    @Cacheable("user")
    @GetMapping("/{id}")
    public @ResponseBody User getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
        return userService.getUserById(id);
    }

    @Secured("ROLE_PM")
    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @Secured("ROLE_PM")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public User getPrincipal(Principal principal) {
        User result = userService.getUserByEmail(principal.getName());

        return result;
    }

    @Secured("ROLE_ANONYMOUS")
    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/index";
        }

        List<Project> projects = projectService.getAllProjectsAsList();
        model.addAttribute("userForm", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projects);

        return "registration";
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addNewUser(user);

        return "redirect:/index";
    }

    @Secured("ROLE_ANONYMOUS")
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/index";
        }

        if (error != null)
            model.addAttribute("error", error.toString());

        if (logout != null)
            model.addAttribute("message", "Has cerrado sesión correctamente.");

        return "login";
    }

}