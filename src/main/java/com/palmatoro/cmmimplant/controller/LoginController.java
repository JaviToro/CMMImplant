package com.palmatoro.cmmimplant.controller;

import com.palmatoro.cmmimplant.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private SecurityServiceImpl securityService;

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
