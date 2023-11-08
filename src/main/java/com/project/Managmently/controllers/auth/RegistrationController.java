package com.project.Managmently.controllers.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/registration")
    public String getFeaturesPage() {
        return "auth/registration";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword ,RedirectAttributes redirectAttributes) {

        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserEmail != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same e-mail address.");

            return "redirect:/registration";
            
        }
        User existingUserUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserUsername != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same username. Try again.");
            
            return "redirect:/registration";
        }
        
        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords must match.");
            
            return "redirect:/registration";
        }
        
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        user.setEmailVerified(false);

        userRepository.saveUser(user);

        // String confirmationLink = "http://localhost:8080/confirm?token=" + token;
        // emailService.sendVerificationEmail(user.getEmail(), confirmationLink);

        // redirectAttributes.addFlashAttribute("successMessage", "Please check your email inbox for instructions to confirm your e-mail address.");

        return "redirect:/";
    }
}
