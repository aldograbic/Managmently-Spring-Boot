package com.project.Managmently.controllers.authRestricted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactInfoController {
    
    @GetMapping("/contact-info")
    public String getContactInfoPage() {
        return "authRestricted/contact-info";
    }
}
