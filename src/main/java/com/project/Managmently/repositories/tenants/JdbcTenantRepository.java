package com.project.Managmently.repositories.tenants;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.Tenant;
import com.project.Managmently.classes.User;
import com.project.Managmently.repositories.roles.RoleRepository;
import com.project.Managmently.repositories.user.UserRepository;
import com.project.Managmently.repositories.user.UserRowMapper;

@Repository
public class JdbcTenantRepository implements TenantRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public JdbcTenantRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void insertTenant(Tenant tenant, int userId) {
        String sql = "INSERT INTO user_tenants(lease_start_date, lease_end_date, rent_amount, security_deposit_amount, property_id, tenant_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, tenant.getLeaseStartDate(), tenant.getLeaseEndDate(), tenant.getRentAmount(), tenant.getSecurityDepositAmount(), tenant.getPropertyId(), tenant.getTenantId());

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
        return jdbcTemplate.query(sql, new TenantRowMapper(userRepository), propertyId);
    }
    
    @Override
    public Tenant getTenantById(int id) {
        String sql = "SELECT * FROM user_tenants where id = ?";
        return jdbcTemplate.queryForObject(sql, new TenantRowMapper(userRepository), id);
    }

    @Override
    public void updateTenant(Tenant tenant) {
        String sql = "UPDATE user_tenants SET lease_start_date = ?, lease_end_date = ?, rent_amount = ?, security_deposit_amount = ? WHERE id = ?";
        jdbcTemplate.update(sql, tenant.getLeaseStartDate(), tenant.getLeaseEndDate(), tenant.getRentAmount(), tenant.getSecurityDepositAmount(), tenant.getId());
    }

    @Override
    public List<Tenant> searchTenants(String query) {
        String sql = "SELECT * FROM user_tenants INNER JOIN users ON user_tenants.tenant_id = users.id " +
             "WHERE LOWER(first_name) LIKE LOWER(?) OR " +
             "LOWER(last_name) LIKE LOWER(?) OR " +
             "LOWER(email) LIKE LOWER(?) OR " +
             "LOWER(lease_start_date) LIKE LOWER(?) OR " +
             "LOWER(lease_end_date) LIKE LOWER(?) OR " +
             "LOWER(rent_amount) LIKE LOWER(?) OR " +
             "LOWER(security_deposit_amount) LIKE LOWER(?)";

        query = "%" + query + "%";
        return jdbcTemplate.query(sql, new TenantRowMapper(userRepository), query, query, query, query, query, query, query);
    }

    @Override
    public int getTenantCountForUserById(int userId) {
        String sql = "SELECT COUNT(*) FROM user_tenants INNER JOIN user_properties ON user_tenants.property_id = user_properties.id WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    @Override
    public String getPropertyNameByTenantId(int tenantId) {
        String sql = "SELECT user_properties.name FROM user_tenants " +
                "INNER JOIN user_properties ON user_tenants.property_id = user_properties.id " +
                "WHERE user_tenants.tenant_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, tenantId);
    }

    @Override
    public User getPropertyOwnerByTenantId(int tenantId) {
        String sql = "SELECT * " +
                    "FROM user_tenants ut " +
                    "JOIN user_properties up ON ut.property_id = up.id " +
                    "JOIN users u ON up.user_id = u.id " +
                    "WHERE ut.tenant_id = ?";
        
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(roleRepository), tenantId);
    }
}
