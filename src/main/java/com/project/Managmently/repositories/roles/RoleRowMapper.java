package com.project.Managmently.repositories.roles;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.project.Managmently.classes.Role;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));

        return role;
    }
}
