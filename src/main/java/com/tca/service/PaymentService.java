package com.tca.service;

import java.util.List;

import com.tca.entity.Payment;
import com.tca.enums.PaymentStatus;

public interface PaymentService {

    boolean savePayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    List<Payment> getPaymentsByUserId(Long userId);
    
    boolean updatePaymentStatus(Long paymentId,PaymentStatus paymentStatus);
    
    Payment getPaymentByTransactionId(String transactionId);
    
    List<Payment> getAllPayment();
}