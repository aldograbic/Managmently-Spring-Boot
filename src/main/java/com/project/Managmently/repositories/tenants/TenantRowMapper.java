package com.project.Managmently.repositories.tenants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.Tenant;

public class TenantRowMapper implements RowMapper<Tenant> {

    @Override
    public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tenant tenant = new Tenant();
        tenant.setId(rs.getInt("id"));

        Date leaseStartDateSql = rs.getDate("lease_start_date");
        if (leaseStartDateSql != null) {
            LocalDate leaseStartDate = leaseStartDateSql.toLocalDate();
            tenant.setLeaseStartDate(leaseStartDate);
        }

        Date leaseEndDateSql = rs.getDate("lease_end_date");
        if (leaseEndDateSql != null) {
            LocalDate leaseEndDate = leaseEndDateSql.toLocalDate();
            tenant.setLeaseEndDate(leaseEndDate);
        }

        tenant.setRentAmount(rs.getBigDecimal("rent_amount"));
        tenant.setSecurityDepositAmount(rs.getBigDecimal("security_deposit_amount"));
        tenant.setPropertyId(rs.getInt("property_id"));

        return tenant;
    }
}
