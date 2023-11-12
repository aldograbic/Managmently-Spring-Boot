package com.project.Managmently.controllers.authRestricted;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.payments.PaymentRepository;
import com.project.Managmently.repositories.user.UserRepository;


@Controller
public class PaymentsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    @GetMapping("/payments")
    public String getPaymentsPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        List<PaymentRecord> paymentRecords = paymentRepository.getPaymentRecordsForUserById(user.getId());
        model.addAttribute("paymentRecords", paymentRecords);

        return "authRestricted/payments";
    }

    @PostMapping("/updatePaymentStatus/{id}")
    public String updatePaymentStatus(@PathVariable("id") int id,
                                    @RequestParam("status") String status,
                                    RedirectAttributes redirectAttributes) {

        try {
            paymentRepository.updatePaymentStatus(status, id);
            redirectAttributes.addFlashAttribute("successMessage", "Payment successfully updated.");                       
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with updating the payment. Try again.");
        }
                              
        return "redirect:/payments";
    }

    @GetMapping("/searchPayments")
    @ResponseBody
    public List<PaymentRecord> searchPayments(@RequestParam String query) {
        return paymentRepository.searchPayments(query);
    }
}
