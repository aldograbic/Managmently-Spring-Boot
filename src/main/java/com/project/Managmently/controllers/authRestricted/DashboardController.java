package com.project.Managmently.controllers.authRestricted;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.payments.PaymentRepository;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        BigDecimal totalRevenueCurrentMonth = paymentRepository.getCompletedPaymentsCountForUserInCurrentMonth(user.getId());
        model.addAttribute("totalRevenue", totalRevenueCurrentMonth);

        BigDecimal totalRevenueLastMonth = paymentRepository.getCompletedPaymentsCountForUserInPreviousMonth(user.getId());

        BigDecimal percentChange;

        if (totalRevenueLastMonth != null && totalRevenueLastMonth.compareTo(BigDecimal.ZERO) != 0) {
            percentChange = (totalRevenueCurrentMonth.subtract(totalRevenueLastMonth))
                    .divide(totalRevenueLastMonth, 2, RoundingMode.HALF_EVEN)
                    .multiply(BigDecimal.valueOf(100));
        } else {
            percentChange = BigDecimal.ZERO;
        }

        model.addAttribute("percentChange", percentChange);

        return "authRestricted/dashboard";
    }
}
