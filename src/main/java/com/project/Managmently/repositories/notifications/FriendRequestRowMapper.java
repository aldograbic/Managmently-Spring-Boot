package com.project.Managmently.repositories.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.FriendRequest;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.user.UserRepository;

public class FriendRequestRowMapper implements RowMapper<FriendRequest> {

    private final UserRepository userRepository;

    public FriendRequestRowMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FriendRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(rs.getInt("id"));
        friendRequest.setSenderId(rs.getInt("sender_id"));
        friendRequest.setReceiverId(rs.getInt("receiver_id"));
        friendRequest.setAccepted(rs.getBoolean("accepted"));

        int senderId = rs.getInt("sender_id");
        User user = userRepository.findById(senderId);
        friendRequest.setSender(user);

        return friendRequest;
    }
}
