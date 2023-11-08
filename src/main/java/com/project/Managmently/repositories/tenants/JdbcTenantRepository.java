package com.project.Managmently.repositories.tenants;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Tenant;

@Repository
public class JdbcTenantRepository implements TenantRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTenantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertTenant(Tenant tenant) {
        String sql = "INSERT INTO user_tenants(first_name, last_name, lease_start_date, lease_end_date, rent_amount, security_deposit_amount, property_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, tenant.getFirstName(), tenant.getLastName(), tenant.getLeaseStartDate(), tenant.getLeaseEndDate(), tenant.getRentAmount(), tenant.getSecurityDepositAmount(), tenant.getPropertyId());
    }

    public void deleteTenant(int id) {
        String sql = "DELETE FROM user_tenants WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Tenant> getTenantsForUserByPropertyId(int propertyId) {
        String sql = "SELECT * FROM user_tenants WHERE property_id = ?";
        return jdbcTemplate.query(sql, new TenantRowMapper(), propertyId);
    }
    
}
