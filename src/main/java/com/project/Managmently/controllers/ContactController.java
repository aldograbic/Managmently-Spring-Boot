package com.project.Managmently.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.repositories.UserRepository;

@Controller
public class ContactController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @PostMapping("/processContactUs")
    public String processContactUs(@RequestParam("name") String name, 
                                @RequestParam("email") String email, 
                                @RequestParam("message") String message, 
                                RedirectAttributes redirectAttributes) {

         userRepository.contact(name, email, message);
         redirectAttributes.addFlashAttribute("successMessage", "Your message has been submitted successfully.");

         return "redirect:/";

    }
}
