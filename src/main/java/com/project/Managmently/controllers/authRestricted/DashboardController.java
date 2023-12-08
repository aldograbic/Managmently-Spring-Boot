package com.project.Managmently.controllers.authRestricted;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.project.Managmently.classes.Message;
import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.classes.User;
import com.project.Managmently.config.EmailService;
import com.project.Managmently.repositories.payments.PaymentRepository;
import com.project.Managmently.repositories.properties.PropertyRepository;
import com.project.Managmently.repositories.tenants.TenantRepository;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        if(user.getRoleId() == 1) {
            List<PaymentRecord> paymentRecordsForTenant = paymentRepository.getPaymentRecordsForTenantByUserId(user.getId());
            model.addAttribute("paymentRecordsForTenant", paymentRecordsForTenant);

            String propertyName = tenantRepository.getPropertyNameByTenantId(user.getId());
            model.addAttribute("propertyName", propertyName);

            User propertyOwner = tenantRepository.getPropertyOwnerByTenantId(user.getId());
            model.addAttribute("propertyOwner", propertyOwner);
        }

        else if(user.getRoleId() == 2) {
            BigDecimal totalRevenueCurrentMonth = paymentRepository.getCompletedPaymentsCountForUserInCurrentMonth(user.getId());
            model.addAttribute("totalRevenue", totalRevenueCurrentMonth != null ? totalRevenueCurrentMonth : BigDecimal.ZERO);

            BigDecimal totalRevenueLastMonth = paymentRepository.getCompletedPaymentsCountForUserInPreviousMonth(user.getId());

            BigDecimal percentChange;

            if (totalRevenueLastMonth != null && totalRevenueLastMonth.compareTo(BigDecimal.ZERO) != 0
                    && totalRevenueCurrentMonth != null && totalRevenueCurrentMonth.compareTo(BigDecimal.ZERO) != 0) {
                percentChange = (totalRevenueCurrentMonth.subtract(totalRevenueLastMonth))
                        .divide(totalRevenueLastMonth, 2, RoundingMode.HALF_EVEN)
                        .multiply(BigDecimal.valueOf(100));
            } else {
                percentChange = BigDecimal.ZERO;
            }
            model.addAttribute("percentChange", percentChange);

            int propertyCount = propertyRepository.getPropertiesCountForUserById(user.getId());
            model.addAttribute("propertyCount", propertyCount);

            int tenantCount = tenantRepository.getTenantCountForUserById(user.getId());
            model.addAttribute("tenantCount", tenantCount);

            int paymentCount = paymentRepository.getPaymentsCountForUserById(user.getId());
            model.addAttribute("paymentCount", paymentCount);
        }
        
        return "authRestricted/dashboard";
    }

    @PostMapping("/sendMessageToPropertyOwner")
    public String sendMessageToPropertyOwner(@ModelAttribute Message message, RedirectAttributes redirectAttributes) {

        User sender = userRepository.findById(message.getSenderId());
        User receiver = userRepository.findById(message.getReceiverId());

        try {
            
            emailService.sendMessage(receiver.getEmail(), "New contact message from " + sender.getFirstName() + " " + sender.getLastName(), "User e-mail: " + sender.getEmail() + "\nMessage: " + message);

            redirectAttributes.addFlashAttribute("successMessage", "Your message has been submitted successfully.");

         } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with sending your message. Please try again.");
            return "redirect:/dashboard";
         }
         
         return "redirect:/dashboard";
    }
}
