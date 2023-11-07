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

import com.project.Managmently.classes.Property;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.PropertyRepository;
import com.project.Managmently.repositories.UserRepository;

@Controller
public class PropertiesController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/properties")
    public String getPropertiesPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        List<Property> properties = propertyRepository.getPropertiesForUserById(user.getId());
        model.addAttribute("properties", properties);

        return "authRestricted/properties";
    }

    @PostMapping("/insertProperty")
    public String insertProperty(@ModelAttribute Property property,
                                RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getId();
        property.setUserId(userId);

        try {
            propertyRepository.insertProperty(property);
            redirectAttributes.addFlashAttribute("successMessage", "Property successfully added.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with adding the property. Try again.");
        }
        
        return "redirect:/properties";
    }
}
