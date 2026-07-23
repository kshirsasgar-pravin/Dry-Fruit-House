package com.tca.service.impl;

import java.util.Collections;
import java.util.List;

import com.tca.dao.PaymentDAO;
import com.tca.dao.impl.PaymentDAOImpl;
import com.tca.entity.Payment;
import com.tca.enums.PaymentStatus;
import com.tca.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public boolean savePayment(Payment payment) {
        if (payment == null || payment.getOrder() == null) {
            System.out.println("Failed to save Payment: Order or Payment is null.");
            return false;
        }

        if (payment.getTransactionId() == null || payment.getTransactionId().trim().isEmpty()
                || payment.getPaymentMode() == null
                || payment.getPayAmount() == null) {
            System.out.println("Failed to process Payment due to insufficient data.");
            return false;
        }

        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        }

        return paymentDAO.savePayment(payment);
    }

    @Override
    public boolean updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {
        if (paymentId == null || paymentId <= 0 || paymentStatus == null) {
            System.out.println("Failed to update Payment status: Invalid parameters.");
            return false;
        }

        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment == null) {
            System.out.println("Payment not found with ID: " + paymentId);
            return false;
        }

        if (payment.getPaymentStatus() != paymentStatus) {
            payment.setPaymentStatus(paymentStatus);
            return paymentDAO.updatePayment(payment);
        } else {
            System.out.println("Payment is already in status: " + paymentStatus);
            return false;
        }
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        if (paymentId == null || paymentId <= 0) {
            System.out.println("Failed to fetch Payment: Invalid paymentId " + paymentId);
            return null;
        }

        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment == null) {
            System.out.println("Payment not found with ID " + paymentId);
        }
        return payment;
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            System.out.println("Failed to fetch Payment: Invalid orderId " + orderId);
            return null;
        }

        Payment payment = paymentDAO.getPaymentByOrderId(orderId);
        if (payment == null) {
            System.out.println("Payment not found with order ID " + orderId);
        }
        return payment;
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            System.out.println("Failed to fetch payments: Invalid userId " + userId);
            return Collections.emptyList();
        }

        List<Payment> paymentList = paymentDAO.getPaymentsByUserId(userId);
        if (paymentList == null || paymentList.isEmpty()) {
            System.out.println("No payments found for user ID " + userId);
            return Collections.emptyList();
        }
        return paymentList;
    }

    @Override
    public Payment getPaymentByTransactionId(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            System.out.println("Invalid transaction ID: " + transactionId);
            return null;
        }

        Payment payment = paymentDAO.getPaymentByTransactionId(transactionId);
        if (payment == null) {
            System.out.println("Payment not found for transactionId " + transactionId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayment() {
        List<Payment> payments = paymentDAO.getAllPayments();
        return payments != null ? payments : Collections.emptyList();
    }
}