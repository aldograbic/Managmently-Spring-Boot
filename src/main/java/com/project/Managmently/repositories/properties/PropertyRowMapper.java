package com.project.Managmently.repositories.properties;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.Property;

public class PropertyRowMapper implements RowMapper<Property> {

    @Override
    public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
        Property property = new Property();
        property.setId(rs.getInt("id"));
        property.setName(rs.getString("name"));
        property.setType(rs.getString("type"));
        property.setLocation(rs.getString("location"));
        property.setDescription(rs.getString("description"));
        property.setSize(rs.getBigDecimal("size"));
        property.setBedrooms(rs.getInt("bedrooms")); 
        property.setBathrooms(rs.getInt("bathrooms"));
        property.setPrice(rs.getBigDecimal("price"));
        property.setStatus(rs.getBoolean("status"));
        property.setUserId(rs.getInt("user_id"));

        return property;
    }
    
}
