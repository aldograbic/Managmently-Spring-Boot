package com.project.Managmently.controllers.authRestricted;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentsController {
    
    @GetMapping("/payments")
    public String getPaymentsPage() {
        return "authRestricted/payments";
    }
}
