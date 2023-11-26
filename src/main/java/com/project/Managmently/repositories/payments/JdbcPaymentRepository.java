package com.project.Managmently.repositories.payments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.classes.Tenant;
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

    // @Override
    // public void savePaymentRecord(PaymentRecord paymentRecord) {
    //     String sql = "INSERT INTO user_payment_records(payment_date, payment_amount, status, tenant_id, user_id) VALUES (?, ?, ?, ?, ?)";
    //     jdbcTemplate.update(sql, paymentRecord.getPaymentDate(), paymentRecord.getPaymentAmount(), paymentRecord.getStatus(), paymentRecord.getTenantId(), paymentRecord.getUserId());
    // }

    // @Scheduled(cron = "0 0 0 * * ?")
    // @Transactional
    // public void generatePaymentsScheduled() {
    //     List<Tenant> tenants = tenantRepository.getAllTenants();

    //     for (Tenant tenant : tenants) {
    //         generatePaymentsForTenant(tenant);
    //     }
    // }

    // @Transactional
    // private void generatePaymentsForTenant(Tenant tenant) {
    //     LocalDate leaseStartDate = tenant.getLeaseStartDate();
    //     LocalDate leaseEndDate = tenant.getLeaseEndDate();
    //     BigDecimal rentAmount = tenant.getRentAmount();

    //     while (leaseStartDate.isBefore(leaseEndDate)) {
    //         PaymentRecord paymentRecord = new PaymentRecord();
    //         paymentRecord.setTenantId(tenant.getId());
    //         paymentRecord.setPaymentDate(leaseStartDate.plusMonths(1));
    //         paymentRecord.setPaymentAmount(rentAmount);
    //         paymentRecord.setStatus("Pending");

    //         savePaymentRecord(paymentRecord);
    //     }
    // }

    @Override
    public BigDecimal getCompletedPaymentsCountForUserInCurrentMonth(int userId) {
        String sql = "SELECT SUM(payment_amount) FROM user_payment_records " +
                    "WHERE user_id = ? " +
                    "AND status = 'Complete' " +
                    "AND YEAR(payment_date) = YEAR(CURDATE()) " +
                    "AND MONTH(payment_date) = MONTH(CURDATE())";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public BigDecimal getCompletedPaymentsCountForUserInPreviousMonth(int userId) {
        String sql = "SELECT SUM(payment_amount) FROM user_payment_records " +
                     "WHERE user_id = ? " +
                     "AND status = 'Complete' " +
                     "AND YEAR(payment_date) = YEAR(CURDATE()) " +
                     "AND MONTH(payment_date) = MONTH(CURDATE()) - 1";
    
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public int getPaymentsCountForUserById(int userId) {
        String sql = "SELECT COUNT(*) FROM user_properties WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

}
