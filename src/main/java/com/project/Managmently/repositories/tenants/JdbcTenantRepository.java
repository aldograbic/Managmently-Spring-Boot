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

    @Override
    public void insertTenant(Tenant tenant, int userId) {
        String sql = "INSERT INTO user_tenants(first_name, last_name, lease_start_date, lease_end_date, rent_amount, security_deposit_amount, property_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, tenant.getFirstName(), tenant.getLastName(), tenant.getLeaseStartDate(), tenant.getLeaseEndDate(), tenant.getRentAmount(), tenant.getSecurityDepositAmount(), tenant.getPropertyId());

        int tenantId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        String initialPaymentSql = "INSERT INTO user_payment_records(payment_date, payment_amount, status, tenant_id, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(initialPaymentSql, tenant.getLeaseStartDate(), tenant.getRentAmount(), "Pending", tenantId, userId);

        String depositPaymentSql = "INSERT INTO user_payment_records(payment_date, payment_amount, status, tenant_id, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(depositPaymentSql, tenant.getLeaseStartDate(), tenant.getSecurityDepositAmount(), "Pending", tenantId, userId);
    }

    @Override
    public void deleteTenant(int id) {
        String sql = "DELETE FROM user_tenants WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Tenant> getTenantsForUserByPropertyId(int propertyId) {
        String sql = "SELECT * FROM user_tenants WHERE property_id = ?";
        return jdbcTemplate.query(sql, new TenantRowMapper(), propertyId);
    }
    
    @Override
    public Tenant getTenantById(int id) {
        String sql = "SELECT * FROM user_tenants where id = ?";
        return jdbcTemplate.queryForObject(sql, new TenantRowMapper(), id);
    }
}
