package com.project.Managmently.repositories.payments;

import java.util.List;

import com.project.Managmently.classes.PaymentRecord;

public interface PaymentRepository {

    List<PaymentRecord> getPaymentRecordsForUserById(int userId);

    void updatePaymentStatus(PaymentRecord paymentRecord);
}
