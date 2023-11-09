package com.project.Managmently.classes;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRecord {
    private int id;
    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
    private String status;
    private int tenantId;
    private int userId;

    private Tenant tenant;

    public PaymentRecord() {}

    public PaymentRecord(LocalDate paymentDate, BigDecimal paymentAmount, String status, int tenantId, int userId) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.status = status;
        this.tenantId = tenantId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
