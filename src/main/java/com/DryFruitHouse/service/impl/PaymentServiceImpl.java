package com.DryFruitHouse.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.DryFruitHouse.repository.OrderDAO;
import com.DryFruitHouse.repository.impl.OrderDAOImpl;
import org.json.JSONObject;

import com.DryFruitHouse.repository.PaymentDAO;
import com.DryFruitHouse.repository.impl.PaymentDAOImpl;
import com.DryFruitHouse.entity.Payment;
import com.DryFruitHouse.enums.PaymentMode;
import com.DryFruitHouse.enums.PaymentStatus;
import com.DryFruitHouse.service.PaymentService;
import com.DryFruitHouse.util.RazorpayUtils;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

public class PaymentServiceImpl implements PaymentService {

	private final PaymentDAO paymentDAO;
	private final OrderDAO orderDAO;
	private static final String RAZORPAY_KEY_ID = "rzp_test_YOUR_KEY";
	private static final String RAZORPAY_KEY_SECRET = "YOUR_SECRET_KEY";

	public PaymentServiceImpl() {

		this.paymentDAO = new PaymentDAOImpl();
		this.orderDAO = new OrderDAOImpl();
	}

	public PaymentServiceImpl(PaymentDAO paymentDAO) {

		this.paymentDAO = paymentDAO;
	    this.orderDAO = new OrderDAOImpl();
	}

	@Override
	public boolean savePayment(Payment payment) {
		if (payment == null || payment.getOrder() == null) {
			System.out.println("Failed to save Payment: Order or Payment is null.");
			return false;
		}

		if (payment.getRazorpayPaymentId() == null || payment.getRazorpayPaymentId().trim().isEmpty()
				|| payment.getPaymentMode() == null || payment.getPayAmount() == null) {
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
	public Payment getPaymentByRazorpayPaymentId(String razorpayPaymentId) {
		if (razorpayPaymentId == null || razorpayPaymentId.trim().isEmpty()) {
			System.out.println("Invalid transaction ID: " + razorpayPaymentId);
			return null;
		}

		Payment payment = paymentDAO.getPaymentByRazorpayPaymentId(razorpayPaymentId);
		if (payment == null) {
			System.out.println("Payment not found for razorpayPaymentId " + razorpayPaymentId);
		}
		return payment;
	}

	@Override
	public List<Payment> getAllPayment() {
		List<Payment> payments = paymentDAO.getAllPayments();
		return payments != null ? payments : Collections.emptyList();
	}

	@Override
	public String createRazorpayOrder(Long orderId, String paymentModeStr) {
		try {
			// Convert String input to Enum
			PaymentMode mode = PaymentMode.valueOf(paymentModeStr.toUpperCase());

			// Check if payment entry already exists for order
			Payment existingPayment = paymentDAO.getPaymentByOrderId(orderId);
			if (existingPayment != null && existingPayment.getPaymentStatus() == PaymentStatus.SUCCESS) {
				System.out.println("Payment already completed for order: " + orderId);
				return null;
			}

			// 1. Handle Cash on Delivery
			if (mode == PaymentMode.COD) {
				Payment payment = null;

				if (existingPayment != null) {
					payment = existingPayment;
				} else {
					payment = new Payment();
				}
				payment.setPaymentMode(PaymentMode.COD);
				payment.setPaymentStatus(PaymentStatus.PENDING);
				payment.setPaymentGateway("N/A");
				payment.setRazorpayPaymentId("COD-" + System.currentTimeMillis());

				if (existingPayment != null) {
					paymentDAO.updatePayment(payment);
				} else {
					paymentDAO.savePayment(payment);
				}
				return "COD_SUCCESS";
			}

			// 2. Handle Razorpay (UPI / Cards / NetBanking)
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);

			// Razorpay accepts amount in paise (1 INR = 100 Paise)
			BigDecimal amountInRupees = null; // Fetch dynamically from Order entity
			
            if(existingPayment != null) {
            	amountInRupees = existingPayment.getPayAmount();
            }
            else {
            	amountInRupees = new BigDecimal("500.00"); 
            }
            
            int amountInPaise = amountInRupees.multiply(new BigDecimal(100)).intValue();
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amountInPaise);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "txn_order_" + orderId);

			Order rzpOrder = razorpay.orders.create(orderRequest);
			String razorpayOrderId = rzpOrder.get("id");
			
			Payment payment;
			if(existingPayment != null) {
			     payment = existingPayment;
			}
			else {
				payment = new Payment();
			}
			payment.setPaymentMode(mode);
			payment.setPaymentStatus(PaymentStatus.PENDING);
			payment.setPaymentGateway("RAZORPAY");
			payment.setRazorpayOrderId(razorpayOrderId);

			if (existingPayment != null) {
				paymentDAO.updatePayment(payment);
			} else {
				paymentDAO.savePayment(payment);
			}
 
			return razorpayOrderId; // Return to frontend checkout script

		} catch (RazorpayException e) {
			System.err.println("Razorpay Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid Payment Mode: " + paymentModeStr);
		}
		return null;
	}

	@Override
	public boolean verifyAndCompletePayment(String razorpayOrderId, String razorpayPaymentId,
			String razorpaySignature) {
		if (razorpayOrderId == null || razorpayPaymentId == null || razorpaySignature == null) {
            return false;
        }

        Payment payment = paymentDAO.getPaymentByRazorpayOrderId(razorpayOrderId);
        if (payment == null) {
            System.out.println("Payment not found for Razorpay Order ID: " + razorpayOrderId);
            return false;
        }

        boolean isValid = RazorpayUtils.verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature, RAZORPAY_KEY_SECRET);

        if (isValid) {
            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            return paymentDAO.updatePayment(payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentDAO.updatePayment(payment);
            System.out.println("Signature verification failed for payment ID: " + razorpayPaymentId);
            return false;
        }
	}
}