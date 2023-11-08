package com.project.Managmently.repositories.tenants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate; // Import LocalDate
import java.sql.Date; // Import Date
import org.springframework.jdbc.core.RowMapper;

import com.project.Managmently.classes.Tenant;

public class TenantRowMapper implements RowMapper<Tenant> {

    @Override
    public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tenant tenant = new Tenant();
        tenant.setId(rs.getInt("id"));
        tenant.setFirstName(rs.getString("first_name"));
        tenant.setLastName(rs.getString("last_name"));
        tenant.setCity(rs.getString("city"));
        tenant.setAddress(rs.getString("address"));
        tenant.setPhoneNumber(rs.getString("phone_number"));
        tenant.setEmail(rs.getString("email"));

        // Convert java.sql.Date to LocalDate
        Date leaseStartDateSql = rs.getDate("lease_start_date");
        if (leaseStartDateSql != null) {
            LocalDate leaseStartDate = leaseStartDateSql.toLocalDate();
            tenant.setLeaseStartDate(leaseStartDate);
        }

        // Convert java.sql.Date to LocalDate
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
