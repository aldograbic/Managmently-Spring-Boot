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
        String sql = "INSERT INTO user_contacts(first_name, last_name, email, phone_number, organization, job_title, notes, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
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
        String sql = "DELETE FROM user_contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Contact> getContactsForUserById(int userId) {
        String sql = "SELECT * FROM user_contacts WHERE user_id = ?";
        return jdbcTemplate.query(sql, new ContactRowMapper(), userId);
    }
    
    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE user_contacts SET first_name = ?, last_name = ?, email = ?, phone_number = ?, organization = ?, job_title = ?, notes = ? WHERE id = ?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhoneNumber(), contact.getOrganization(), contact.getJobTitle(),
         contact.getNotes(), contact.getId());
    }

    @Override
    public List<Contact> searchContacts(String query) {
        String sql = "SELECT * FROM user_contacts " +
             "WHERE LOWER(first_name) LIKE LOWER(?) OR " +
             "LOWER(last_name) LIKE LOWER(?) OR " +
             "LOWER(email) LIKE LOWER(?) OR " +
             "LOWER(phone_number) LIKE LOWER(?) OR " +
             "LOWER(organization) LIKE LOWER(?) OR " +
             "LOWER(job_title) LIKE LOWER(?) OR " +
             "LOWER(notes) LIKE LOWER(?)";

        query = "%" + query + "%";
        return jdbcTemplate.query(sql, new ContactRowMapper(), query, query, query, query, query, query, query);
    }
}
