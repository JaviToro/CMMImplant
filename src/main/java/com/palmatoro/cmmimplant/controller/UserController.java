package com.palmatoro.cmmimplant.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.palmatoro.cmmimplant.domain.Project;
import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.service.ProjectService;
import com.palmatoro.cmmimplant.service.SecurityServiceImpl;
import com.palmatoro.cmmimplant.service.UserService;
import com.palmatoro.cmmimplant.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityServiceImpl securityService;

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

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = {"/list", "/list/error/{code}"}, method = RequestMethod.GET)
    public String list(Model model, @PathVariable(value = "code", required = false) Integer errorCode) {

        if (errorCode != null){
            model.addAttribute("error", "Se ha producido un error");
        }
        
        List<User> results = new ArrayList<User>();

        userService.getAllUsers().forEach(results::add);

        model.addAttribute("results", results);

        return "user/list";
    }

    @Secured("ROLE_PM")
    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @RequestMapping("/delete/{id}")
    @Secured({"ROLE_ADMIN"})
    public String deleteById(@PathVariable(value = "id") Integer id) {

        try {
            userService.deleteUserById(id);
        } catch (Exception e) {
            return "redirect:/user/list/error/1";
        }

        return "redirect:/user/list";

    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public User getPrincipal(Principal principal) {
        User result = userService.getUserByEmail(principal.getName());

        return result;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/registration")
    public String registration(Model model) {
        // if (securityService.isAuthenticated()) {
        //     return "redirect:/index";
        // }

        List<Project> projects = projectService.getAllProjectsAsList();
        model.addAttribute("userForm", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projects);

        return "user/registration";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        userService.addNewUser(user);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/index";
        }

        if (error != null)
            model.addAttribute("error", error.toString());

        if (logout != null)
            model.addAttribute("message", "Has cerrado sesión correctamente.");

        return "user/login";
    }

}