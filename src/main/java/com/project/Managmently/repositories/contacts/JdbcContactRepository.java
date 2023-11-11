package com.project.Managmently.repositories.contacts;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Contact;

@Repository
public class JdbcContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public void deleteContact(int id) {
        String sql = "DELETE FROM user_contact_info WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Contact> getContactsForUserById(int userId) {
        String sql = "SELECT * FROM user_contact_info WHERE user_id = ?";
        return jdbcTemplate.query(sql, new ContactRowMapper(), userId);
    }
    
    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE user_contact_info SET first_name = ?, last_name = ?, email = ?, phone_number = ?, organization = ?, job_title = ?, notes = ? WHERE id = ?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhoneNumber(), contact.getOrganization(), contact.getJobTitle(),
         contact.getNotes(), contact.getId());
    }
}
