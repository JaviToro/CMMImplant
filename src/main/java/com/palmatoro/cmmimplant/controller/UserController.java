package com.palmatoro.cmmimplant.User;

import com.palmatoro.cmmimplant.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String userIndex() {
        return "Greetings from CMMImplant, using Spring Boot!";
    }

    @PostMapping(path = "(/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String surname, @RequestParam String acronym, @RequestParam User.UserRole userRole, @RequestParam String email, @RequestParam String password, @RequestParam Project project) {

        User u = new User();
        u.setName(name);
        u.setSurname(surname);
        u.setAcronym(acronym);
        u.setUserRole(userRole);
        u.setEmail(email);
        u.setPassword(password);
        u.setProject(project);
        userRepository.save(u);
        return "Saved.";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}