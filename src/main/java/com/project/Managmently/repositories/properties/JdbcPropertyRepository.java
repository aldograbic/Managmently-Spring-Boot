package com.project.Managmently.repositories.properties;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Property;

@Repository
public class JdbcPropertyRepository implements PropertyRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPropertyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertProperty(Property property) {
        String sql = "INSERT INTO user_properties(name, type, location, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, property.getName(), property.getType(), property.getLocation(), property.getUserId());
    }

    @Override
    public List<Property> getPropertiesForUserById(int userId) {
        String sql = "SELECT * FROM user_properties WHERE user_id = ?";
        return jdbcTemplate.query(sql, new PropertyRowMapper(), userId);
    }

    @Override
    public void deleteProperty(int id) {
        String sql = "DELETE FROM user_properties WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int getPropertyIdByName(String name) {
        String sql = "SELECT id FROM user_properties WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }
}
