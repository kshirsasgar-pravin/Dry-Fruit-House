package com.DryFruitHouse.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.DryFruitHouse.enums.PaymentMode;
import com.DryFruitHouse.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode", nullable = false)
	private PaymentMode paymentMode;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false)
	private PaymentStatus paymentStatus;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "razorpay_order_id")
	private String razorpayOrderId;

	@Column(name = "razorpay_payment_id")
	private String razorpayPaymentId;

	@Column(name="razorpay_signature")
	private String razorpaySignature;

	@Column(name = "payment_gateway", nullable = false)
	private String paymentGateway;

	@CreationTimestamp
	@Column(name = "payment_date", nullable = false, updatable = false)
	private LocalDateTime paymentDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	public Payment() {
	}

	public Payment(PaymentMode paymentMode, PaymentStatus paymentStatus, BigDecimal amount, String razorpayPaymentId,
			String paymentGateway, Order order) {
		super();
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.amount = amount;
		this.razorpayPaymentId = razorpayPaymentId;
		this.paymentGateway = paymentGateway;
		this.order = order;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getPayAmount() {
		return amount;
	}

	public void setPayAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentMode=" + paymentMode + ", paymentStatus=" + paymentStatus
				+ ", amount=" + amount + ", razorpayPaymentId=" + razorpayPaymentId + ", paymentGateway=" + paymentGateway
				+ ", paymentDate=" + paymentDate + "]";
	}

}