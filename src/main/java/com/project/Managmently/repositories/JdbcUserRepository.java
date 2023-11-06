package com.project.Managmently.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Contact;
import com.project.Managmently.classes.User;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, first_name, last_name, city, address, phone_number, email, role_id, profile_image, email_verified, confirmation_token FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password, first_name, last_name, city, address, phone_number, email, role_id, profile_image, email_verified, confirmation_token FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);

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
    public void insertContact(Contact contact) {
        String sql = "INSERT INTO user_contact_info(first_name, last_name, email, phone_number, organization, job_title, notes, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            contact.getFirstName(),
            contact.getLastName(),
            contact.getEmail(),
            contact.getPhoneNumber(),
            contact.getOrganization(),
            contact.getJobTitle(),
            contact.getNotes(),
            contact.getUserId()
        );
    }

    @Override
    public List<Contact> getContactsForUserById(int userId) {
        String sql = "SELECT * FROM user_contact_info WHERE user_id = ?";
        return jdbcTemplate.query(sql, new ContactRowMapper(), userId);
    }
}
