package com.project.Managmently.controllers.authRestricted;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.classes.Property;
import com.project.Managmently.classes.Tenant;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.properties.PropertyRepository;
import com.project.Managmently.repositories.tenants.TenantRepository;
import com.project.Managmently.repositories.user.UserRepository;

@Controller
public class TenantsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;
    
    @GetMapping("/tenants")
    public String getTenantsPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        List<Property> properties = propertyRepository.getPropertiesForUserById(user.getId());
        model.addAttribute("properties", properties);

        Map<String, List<Tenant>> tenantsByProperty = properties.stream()
            .collect(Collectors.toMap(
                Property::getName,
                property -> tenantRepository.getTenantsForUserByPropertyId(property.getId()),
                (existingValue, newValue) -> existingValue,
                LinkedHashMap::new
            ));
        model.addAttribute("tenantsByProperty", tenantsByProperty);


        return "authRestricted/tenants";
    }

    @PostMapping("/insertTenant")
    public String insertTenant(@ModelAttribute Tenant tenant,
                            @RequestParam("property") String propertyName,
                            RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getId();

        int propertyId = propertyRepository.getPropertyIdByName(propertyName);
        tenant.setPropertyId(propertyId);

        try {
            PaymentRecord initialPayment = new PaymentRecord(tenant.getLeaseStartDate(), tenant.getRentAmount(), "Pending", tenant.getId(), userId);
            PaymentRecord initialSecurityDepositPayment = new PaymentRecord(tenant.getLeaseStartDate(), tenant.getSecurityDepositAmount(), "Pending", tenant.getId(), userId);
            tenant.getPaymentRecords().add(initialPayment);
            tenant.getPaymentRecords().add(initialSecurityDepositPayment);

            tenantRepository.insertTenant(tenant, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Tenant successfully added.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with adding the tenant. Try again.");
        }

        return "redirect:/tenants";
    }

    @PostMapping("/deleteTenant/{id}")
    public String deleteTenant(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {

        try {
            tenantRepository.deleteTenant(id);
            redirectAttributes.addFlashAttribute("successMessage", "Tenant successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with deleting the tenant. Try again.");
        }                            
        
        return "redirect:/tenants";
    }

    @PostMapping("/updateTenant")
    public String updateContact(@ModelAttribute Tenant tenant, RedirectAttributes redirectAttributes) {
        try {
            tenantRepository.updateTenant(tenant);
            redirectAttributes.addFlashAttribute("successMessage", "Tenant successfully updated.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with updating the tenant. Try again.");

        }

        return "redirect:/tenants";
    }
}
