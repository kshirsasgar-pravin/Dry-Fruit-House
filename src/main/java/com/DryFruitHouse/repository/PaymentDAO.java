package com.DryFruitHouse.repository;

import java.util.List;

import com.DryFruitHouse.entity.Payment;

public interface PaymentDAO {

    boolean savePayment(Payment payment);

    boolean updatePayment(Payment payment);

    boolean deletePayment(Long paymentId);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    Payment getPaymentByRazorpayPaymentId(String transactionId);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByUserId(Long userId);
    
    Payment getPaymentByRazorpayOrderId(String razorpayOrderId);
   
}