package com.project.Managmently.repositories.contacts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.Contact;

public class ContactRowMapper implements RowMapper<Contact>{
    
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setEmail(rs.getString("email"));
        contact.setPhoneNumber(rs.getString("phone_number"));
        contact.setOrganization(rs.getString("organization"));
        contact.setJobTitle(rs.getString("job_title")); 
        contact.setNotes(rs.getString("notes"));
        contact.setUserId(rs.getInt("user_id"));

        return contact;
    }
}
