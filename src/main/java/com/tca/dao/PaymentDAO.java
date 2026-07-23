package com.tca.dao;

import java.util.List;
import com.tca.entity.Payment;

public interface PaymentDAO {

    boolean savePayment(Payment payment);

    boolean updatePayment(Payment payment);

    boolean deletePayment(Long paymentId);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    Payment getPaymentByTransactionId(String transactionId);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByUserId(Long userId);
    
   
}