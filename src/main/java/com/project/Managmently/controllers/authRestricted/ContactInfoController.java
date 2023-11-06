package com.project.Managmently.controllers.authRestricted;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.Contact;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.UserRepository;

@Controller
public class ContactInfoController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/contact-info")
    public String getContactInfoPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        List<Contact> userContacts = userRepository.getContactsForUserById(user.getId());
        model.addAttribute("userContacts", userContacts);

        return "authRestricted/contact-info";
    }

    @PostMapping("/insertContact")
    public String insertContact(@ModelAttribute Contact contact,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getId();
        contact.setUserId(userId);

        try {
            userRepository.insertContact(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Contact successfully added.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with adding the contact. Try again.");
        }
        
        return "redirect:/contact-info";
        
    }
}
