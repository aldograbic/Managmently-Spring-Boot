package com.project.Managmently.controllers.authRestricted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "authRestricted/dashboard";
    }
}
