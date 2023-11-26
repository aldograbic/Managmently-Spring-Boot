package com.project.Managmently.controllers.authRestricted;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.Contact;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.contacts.ContactRepository;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class ContactInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;
    
    @GetMapping("/contact-info")
    public String getContactInfoPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        List<Contact> userContacts = contactRepository.getContactsForUserById(user.getId());
        model.addAttribute("userContacts", userContacts);

        return "authRestricted/contact-info";
    }

    @PostMapping("/insertContact")
    public String insertContact(@ModelAttribute Contact contact,
                                RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getId();
        contact.setUserId(userId);

        try {
            contactRepository.insertContact(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Contact successfully added.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with adding the contact. Please try again.");
        }
        
        return "redirect:/contact-info";
    }

    @PostMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

        try {
            contactRepository.deleteContact(id);
            redirectAttributes.addFlashAttribute("successMessage", "Contact successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with deleting the contact. Please try again.");

        }

        return "redirect:/contact-info";
    }

    @PostMapping("/updateContact")
    public String updateContact(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        try {
            contactRepository.updateContact(contact);
            redirectAttributes.addFlashAttribute("successMessage", "Contact successfully updated.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with updating the contact. Please try again.");
        }

        return "redirect:/contact-info";
    }

    @GetMapping("/searchContacts")
    @ResponseBody
    public List<Contact> searchContacts(@RequestParam String query) {
        return contactRepository.searchContacts(query);
    }

    @GetMapping("/searchUsers")
    @ResponseBody
    public List<User> searchUsers(@RequestParam String query) {
        return userRepository.searchUsers(query);
    }
}
