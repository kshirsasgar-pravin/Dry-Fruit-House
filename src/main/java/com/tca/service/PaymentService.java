package com.tca.service;

import java.util.List;
import com.tca.entity.Payment;

public interface PaymentService {

    boolean processPayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    List<Payment> getPaymentsByCustomerId(Long userId);
}