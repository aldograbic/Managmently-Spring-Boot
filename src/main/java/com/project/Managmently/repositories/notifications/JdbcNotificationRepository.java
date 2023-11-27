package com.project.Managmently.repositories.notifications;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.FriendRequest;

@Repository
public class JdbcNotificationRepository implements NotificationRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcNotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FriendRequest> getAllFriendRequestsForUserById(int userId) {
        String sql = "SELECT * FROM friend_request WHERE receiver_id = ?";
        return jdbcTemplate.query(sql, new FriendRequestRowMapper(), userId);
    };
}
