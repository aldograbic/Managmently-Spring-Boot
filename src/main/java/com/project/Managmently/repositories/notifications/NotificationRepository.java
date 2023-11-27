package com.project.Managmently.repositories.notifications;

import java.util.List;

import com.project.Managmently.classes.FriendRequest;

public interface NotificationRepository {
    
    List<FriendRequest> getAllFriendRequestsForUserById(int userId);
    
}
