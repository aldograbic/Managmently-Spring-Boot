package com.project.Managmently.repositories.notifications;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.FriendRequest;
import com.project.Managmently.repositories.user.UserRepository;

@Repository
public class JdbcNotificationRepository implements NotificationRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public JdbcNotificationRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<FriendRequest> getAllFriendRequestsForUserById(int userId) {
        String sql = "SELECT * FROM friend_request WHERE receiver_id = ? AND accepted = false";
        return jdbcTemplate.query(sql, new FriendRequestRowMapper(userRepository), userId);
    };

    @Override
    public void updateAcceptedFriendRequest(int id) {
        String sql = "UPDATE friend_request SET accepted = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
