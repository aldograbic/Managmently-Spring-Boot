package com.project.Managmently.repositories.contacts;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.roles.RoleRepository;
import com.project.Managmently.repositories.user.UserRowMapper;

@Repository
public class JdbcContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleRepository roleRepository;

    public JdbcContactRepository(JdbcTemplate jdbcTemplate, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addNewContact(int userId, int contactId) {
        String sql = "INSERT INTO user_contacts(user_id, contact_id) VALUES (?, ?)";
        
        jdbcTemplate.update(sql, userId, contactId);
    }
    
    @Override
    public void deleteContact(int id) {
        String sql = "DELETE FROM user_contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> getContactsForUserById(int userId) {
        String sql = "SELECT users.* FROM users " +
                     "JOIN user_contacts ON (user_contacts.user_id = users.id OR user_contacts.contact_id = users.id) " +
                     "WHERE (user_contacts.user_id = ? OR user_contacts.contact_id = ?) AND users.id != ?";
        return jdbcTemplate.query(sql, new UserRowMapper(roleRepository), userId, userId, userId);
    }
    
    @Override
    public List<User> searchContacts(String query) {
        String sql = "SELECT * FROM user_contacts " +
             "WHERE LOWER(first_name) LIKE LOWER(?) OR " +
             "LOWER(last_name) LIKE LOWER(?) OR " +
             "LOWER(email) LIKE LOWER(?) OR " +
             "LOWER(phone_number) LIKE LOWER(?)";

        query = "%" + query + "%";
        return jdbcTemplate.query(sql, new UserRowMapper(roleRepository), query, query, query, query);
    }

    @Override
    public void sendFriendRequest(int userId, int contactUserId) {
        String sql = "INSERT INTO friend_request(sender_id, receiver_id, accepted) " +
            "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, contactUserId, false);
    }

    @Override
    public List<User> getTenantsForUserById(int userId) {
        String sql = "SELECT users.* FROM users " +
                     "JOIN user_contacts ON (user_contacts.user_id = users.id OR user_contacts.contact_id = users.id) " +
                     "WHERE (user_contacts.user_id = ? OR user_contacts.contact_id = ?) AND users.id != ? AND users.role_id = 1";
        return jdbcTemplate.query(sql, new UserRowMapper(roleRepository), userId, userId, userId);
    }
}
