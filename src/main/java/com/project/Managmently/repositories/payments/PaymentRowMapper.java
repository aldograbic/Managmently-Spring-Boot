package com.project.Managmently.repositories.payments;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.PaymentRecord;
import com.project.Managmently.classes.Tenant;
import com.project.Managmently.repositories.tenants.TenantRepository;

public class PaymentRowMapper implements RowMapper<PaymentRecord> {

    private TenantRepository tenantRepository;

    public PaymentRowMapper(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public PaymentRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setId(rs.getInt("id"));

        Date paymentDateSql = rs.getDate("payment_date");
        if (paymentDateSql != null) {
            LocalDate paymentDate = paymentDateSql.toLocalDate();
            paymentRecord.setPaymentDate(paymentDate);
        }

        paymentRecord.setPaymentAmount(rs.getBigDecimal("payment_amount"));
        paymentRecord.setTenantId(rs.getInt("tenant_id")); 
        paymentRecord.setUserId(rs.getInt("user_id"));

        int tenantId = rs.getInt("tenant_id");
        Tenant tenant = tenantRepository.getTenantById(tenantId);
        paymentRecord.setTenant(tenant);

        return paymentRecord;
    }
    
}
