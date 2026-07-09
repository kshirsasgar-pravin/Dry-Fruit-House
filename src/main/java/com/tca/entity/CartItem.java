package com.tca.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
	
	@Id
	@Column(name="cart_item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cartItemId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cart_id",nullable=false)
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
    
	@Column(name="quantity",nullable=false)
	private Integer quantity;

	@Column(name="unit_price",nullable = false)
	private BigDecimal unitPrice;
    
	@Column(name="subtotal",nullable=false)
	private BigDecimal subtotal;
	
	public CartItem() {}

	public CartItem(Cart cart, Product product, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
		super();
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.subtotal = subtotal;
	}

	
   	
	
	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity + ", unitPrice=" + unitPrice
				+ ", subtotal=" + subtotal + "]";
	}
	
	
}
