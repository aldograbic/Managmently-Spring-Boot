package com.project.Managmently.controllers.authRestricted;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.Managmently.classes.FriendRequest;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.notifications.NotificationRepository;
import com.project.Managmently.repositories.user.UserRepository;

@ControllerAdvice
public class NotificationsController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;
    
    @ModelAttribute("friendRequests")
    public List<FriendRequest> showFriendRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);

            if (user != null) {
                int userId = user.getId();
                return notificationRepository.getAllFriendRequestsForUserById(userId);
            }
        }

        return List.of();
    }
}
