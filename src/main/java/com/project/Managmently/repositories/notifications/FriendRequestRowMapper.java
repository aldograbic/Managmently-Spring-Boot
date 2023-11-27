package com.project.Managmently.repositories.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.FriendRequest;

public class FriendRequestRowMapper implements RowMapper<FriendRequest> {

    @Override
    public FriendRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(rs.getInt("id"));
        friendRequest.setSenderId(rs.getInt("sender_id"));
        friendRequest.setReceiverId(rs.getInt("receiver_id"));
        friendRequest.setAccepted(rs.getBoolean("accepted"));

        return friendRequest;
    }
}
