package com.project.Managmently.repositories.payments;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.repositories.tenants.TenantRepository;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TenantRepository tenantRepository;

    public JdbcPaymentRepository(JdbcTemplate jdbcTemplate, TenantRepository tenantRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tenantRepository = tenantRepository;
    }
    
    @Override
    public List<PaymentRecord> getPaymentRecordsForUserById(int userId) {
        String sql = "SELECT * FROM user_payment_records WHERE user_id = ?";
        return jdbcTemplate.query(sql, new PaymentRowMapper(tenantRepository), userId);
    }

    @Override
    public void updatePaymentStatus(String status, int id) {
        String sql = "UPDATE user_payment_records SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    @Override
    public List<PaymentRecord> searchPayments(String query) {
        String sql = "SELECT * FROM user_payment_records INNER JOIN user_tenants ON user_payment_records.tenant_id = user_tenants.id " +
             "WHERE LOWER(payment_amount) LIKE LOWER(?) OR " +
             "LOWER(payment_date) LIKE LOWER(?) OR " +
             "LOWER(status) LIKE LOWER(?) OR " +
             "LOWER(first_name) LIKE LOWER(?) OR " +
             "LOWER(last_name) LIKE LOWER(?)";

        query = "%" + query + "%";
        return jdbcTemplate.query(sql, new PaymentRowMapper(tenantRepository), query, query, query, query, query);
    }
}
