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
    public void updatePaymentStatus(PaymentRecord paymentRecord) {
        String sql = "UPDATE user_payments SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, paymentRecord.getStatus(), paymentRecord.getId());
    }
}
