package com.project.Managmently.controllers.authRestricted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/account")
    public String getContactInfoPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "authRestricted/account";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        try {
            userRepository.updateUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Account information successfully updated.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with updating your information. Try again.");
        }

        return "redirect:/account";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        try {
            userRepository.deleteUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Account successfully deleted.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with deleting your account. Try again.");
            return "redirect:account";
        }

        return "redirect:/";
    }
}
