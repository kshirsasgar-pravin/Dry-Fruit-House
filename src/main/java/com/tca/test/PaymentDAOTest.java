package com.tca.test;

import java.math.BigDecimal;
import java.util.List;

import com.tca.dao.impl.CustomerDAOImpl;
import com.tca.dao.impl.OrderDAOImpl;
import com.tca.dao.impl.PaymentDAOImpl;
import com.tca.entity.Customer;
import com.tca.entity.Order;
import com.tca.entity.Payment;
import com.tca.enums.OrderStatus;
import com.tca.enums.PaymentMode;
import com.tca.enums.PaymentStatus;

public class PaymentDAOTest {

	public void testPayment() {

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();
		OrderDAOImpl orderDAO = new OrderDAOImpl();
		PaymentDAOImpl paymentDAO = new PaymentDAOImpl();

		// Fetch existing customer
		Customer customer = customerDAO.getCustomerById(1L);

		if (customer == null) {
			System.out.println("Customer not found.");
			return;
		}

		// ================= CREATE ORDER =================

		Order order = new Order();
		order.setCustomer(customer);
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

		// If you rename the methods:
		// payment.setAmount(...);

		payment.setPayAmount(new BigDecimal("1500.00"));

		payment.setTransactionId("TXN123456789");
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

		Payment paymentByTxn = paymentDAO.getPaymentByTransactionId("TXN123456789");

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

		List<Payment> customerPayments =
				paymentDAO.getPaymentsByCustomerId(customer.getUserId());

		if (customerPayments != null && !customerPayments.isEmpty()) {
			for (Payment p : customerPayments) {
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