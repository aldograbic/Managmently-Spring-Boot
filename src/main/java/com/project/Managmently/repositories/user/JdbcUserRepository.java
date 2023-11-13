package com.project.Managmently.repositories.user;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.roles.RoleRepository;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleRepository roleRepository;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, first_name, last_name, city, address, phone_number, email, role_id, profile_image, email_verified, confirmation_token FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(roleRepository), username);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, city, address, phone_number, email, role_id, profile_image, email_verified, confirmation_token FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(roleRepository), email);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, password, first_name, last_name, city, address, phone_number, email, role_id, email_verified, confirmation_token) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getCity(), user.getAddress(),
            user.getPhoneNumber(), user.getEmail(), user.getRoleId(), user.isEmailVerified(), user.getConfirmationToken());
    }

    @Override
    public void contact(String name, String email, String message) {
        String sql = "INSERT INTO contact_messages(name, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, email, message);
    }

    @Override
    public void updateUser(User user) {
         String sql = "UPDATE users SET first_name = ?, last_name = ?, city = ?, address = ?, phone_number = ?, email = ? WHERE id = ?";
         jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getCity(), user.getAddress(),
            user.getPhoneNumber(), user.getEmail(), user.getId());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
    }
}
