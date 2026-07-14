package com.tca.dao.impl;

import java.util.List;

import com.tca.dao.PaymentDAO;
import com.tca.entity.Payment;

public class PaymentDAOImpl implements PaymentDAO {

	@Override
	public boolean savePayment(Payment payment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePayment(Payment payment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePayment(Long paymentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Payment getPaymentById(Long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payment getPaymentByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payment getPaymentByTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllPayments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getPaymentsByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
