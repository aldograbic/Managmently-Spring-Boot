package com.project.Managmently.repositories.payments;

import java.math.BigDecimal;
import java.util.List;

import com.project.Managmently.classes.PaymentRecord;

public interface PaymentRepository {

    List<PaymentRecord> getPaymentRecordsForUserById(int userId);

    void updatePaymentStatus(String status, int id);

    List<PaymentRecord> searchPayments(String query);

    // void savePaymentRecord(PaymentRecord paymentRecord);

    BigDecimal getCompletedPaymentsCountForUserInCurrentMonth(int userId);

    BigDecimal getCompletedPaymentsCountForUserInPreviousMonth(int userId);
}
