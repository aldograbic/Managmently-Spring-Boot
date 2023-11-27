package com.project.Managmently.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tenant {

    private int id;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private BigDecimal rentAmount;
    private BigDecimal securityDepositAmount;
    private int propertyId;
    private int tenantId;

    private List<PaymentRecord> paymentRecords = new ArrayList<>();

    public Tenant() {}

    public Tenant(int id, LocalDate leaseStartDate, LocalDate leaseEndDate, BigDecimal rentAmount,
            BigDecimal securityDepositAmount, int propertyId, int tenantId) {
        this.id = id;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.rentAmount = rentAmount;
        this.securityDepositAmount = securityDepositAmount;
        this.propertyId = propertyId;
        this.tenantId = tenantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public BigDecimal getSecurityDepositAmount() {
        return securityDepositAmount;
    }

    public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
        this.securityDepositAmount = securityDepositAmount;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public List<PaymentRecord> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<PaymentRecord> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }
}
