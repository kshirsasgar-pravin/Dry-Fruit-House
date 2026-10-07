package com.DryFruitHouse.service;

import java.util.List;

import com.DryFruitHouse.entity.Payment;
import com.DryFruitHouse.enums.PaymentStatus;

public interface PaymentService {

    

    boolean savePayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    Payment getPaymentByOrderId(Long orderId);

    List<Payment> getPaymentsByUserId(Long userId);
    
    boolean updatePaymentStatus(Long paymentId,PaymentStatus paymentStatus);
    
    Payment getPaymentByRazorpayPaymentId(String transactionId);
    
    List<Payment> getAllPayment();
    
    String createRazorpayOrder(Long orderId, String paymentModeStr);

    boolean verifyAndCompletePayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature);
}