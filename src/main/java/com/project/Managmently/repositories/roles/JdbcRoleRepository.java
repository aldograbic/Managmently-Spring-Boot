package com.project.Managmently.repositories.roles;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Role;

@Repository
public class JdbcRoleRepository implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Role findById(int roleId) {
        String sql = "SELECT id, name FROM roles WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId);
    }
}
