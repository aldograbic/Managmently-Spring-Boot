package com.project.Managmently.controllers.authRestricted;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TenantsController {
    
    @GetMapping("/tenants")
    public String getTenantsPage() {
        return "authRestricted/tenants";
    }
}
