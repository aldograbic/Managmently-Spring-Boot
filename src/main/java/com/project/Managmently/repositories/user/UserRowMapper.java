package com.project.Managmently.repositories.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.Role;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.roles.RoleRepository;

public class UserRowMapper implements RowMapper<User> {

    private final RoleRepository roleRepository;

    public UserRowMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCity(rs.getString("city"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setEmailVerified(rs.getBoolean("email_verified"));
        user.setConfirmationToken(rs.getString("confirmation_token"));
        user.setRoleId(rs.getInt("role_id"));
        user.setProfileImage(rs.getString("profile_image"));

        int roleId = rs.getInt("role_id");
        Role role = roleRepository.findById(roleId);
        user.setRole(role);

        return user;
    }
    
}
