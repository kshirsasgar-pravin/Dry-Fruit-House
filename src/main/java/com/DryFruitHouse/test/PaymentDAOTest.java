package com.DryFruitHouse.test;

import java.math.BigDecimal;
import java.util.List;

import com.DryFruitHouse.repository.impl.OrderDAOImpl;
import com.DryFruitHouse.repository.impl.PaymentDAOImpl;
import com.DryFruitHouse.repository.impl.UserDAOImpl;
import com.DryFruitHouse.entity.Order;
import com.DryFruitHouse.entity.Payment;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.enums.OrderStatus;
import com.DryFruitHouse.enums.PaymentMode;
import com.DryFruitHouse.enums.PaymentStatus;

public class PaymentDAOTest {

	public void testPayment() {

		UserDAOImpl userDAO = new UserDAOImpl();
		OrderDAOImpl orderDAO = new OrderDAOImpl();
		PaymentDAOImpl paymentDAO = new PaymentDAOImpl();

		// Fetch existing user
		User user = userDAO.getUserById(1L);

		if (user == null) {
			System.out.println("User not found.");
			return;
		}

		// ================= CREATE ORDER =================

		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("1500.00"));
		order.setOrderStatus(OrderStatus.CONFIRMED);

		if (!orderDAO.saveOrder(order)) {
			System.out.println("Failed to create order.");
			return;
		}

		// ================= SAVE PAYMENT =================

		System.out.println("\n========== SAVE PAYMENT ==========");

		Payment payment = new Payment();

		payment.setOrder(order);
		payment.setPaymentMode(PaymentMode.UPI);
		payment.setPaymentStatus(PaymentStatus.SUCCESS);
		payment.setPayAmount(new BigDecimal("1500.00"));
		payment.setRazorpayPaymentId("TXN123456789");
		payment.setPaymentGateway("Razorpay");

		if (paymentDAO.savePayment(payment)) {
			System.out.println("Payment saved successfully.");
		} else {
			System.out.println("Failed to save payment.");
			return;
		}

		// ================= UPDATE PAYMENT =================

		System.out.println("\n========== UPDATE PAYMENT ==========");

		payment.setPaymentGateway("PhonePe");

		if (paymentDAO.updatePayment(payment)) {
			System.out.println("Payment updated successfully.");
		} else {
			System.out.println("Failed to update payment.");
		}

		// ================= GET PAYMENT BY ID =================

		System.out.println("\n========== GET PAYMENT BY ID ==========");

		Payment paymentById = paymentDAO.getPaymentById(payment.getPaymentId());

		if (paymentById != null) {
			System.out.println(paymentById);
		} else {
			System.out.println("Payment not found.");
		}

		// ================= GET PAYMENT BY ORDER ID =================

		System.out.println("\n========== GET PAYMENT BY ORDER ID ==========");

		Payment paymentByOrder = paymentDAO.getPaymentByOrderId(order.getOrderId());

		if (paymentByOrder != null) {
			System.out.println(paymentByOrder);
		} else {
			System.out.println("Payment not found.");
		}

		// ================= GET PAYMENT BY TRANSACTION ID =================

		System.out.println("\n========== GET PAYMENT BY TRANSACTION ID ==========");

		Payment paymentByTxn = paymentDAO.getPaymentByRazorpayPaymentId("TXN123456789");

		if (paymentByTxn != null) {
			System.out.println(paymentByTxn);
		} else {
			System.out.println("Payment not found.");
		}

		// ================= GET ALL PAYMENTS =================

		System.out.println("\n========== GET ALL PAYMENTS ==========");

		List<Payment> paymentList = paymentDAO.getAllPayments();

		if (paymentList != null && !paymentList.isEmpty()) {
			for (Payment p : paymentList) {
				System.out.println(p);
			}
		} else {
			System.out.println("No payments found.");
		}

		// ================= GET PAYMENTS BY USER ID =================

		System.out.println("\n========== GET PAYMENTS BY USER ID ==========");

		List<Payment> userPayments = paymentDAO.getPaymentsByUserId(user.getUserId());

		if (userPayments != null && !userPayments.isEmpty()) {
			for (Payment p : userPayments) {
				System.out.println(p);
			}
		} else {
			System.out.println("No payments found.");
		}

		// ================= DELETE PAYMENT =================

		System.out.println("\n========== DELETE PAYMENT ==========");

		if (paymentDAO.deletePayment(payment.getPaymentId())) {
			System.out.println("Payment deleted successfully.");
		} else {
			System.out.println("Failed to delete payment.");
		}

		// ================= DELETE ORDER =================

		orderDAO.deleteOrder(order.getOrderId());
	}
}