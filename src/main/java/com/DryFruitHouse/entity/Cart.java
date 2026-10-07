package com.DryFruitHouse.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="carts")
public class Cart {
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Long cartId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@OneToMany(mappedBy="cart",fetch=FetchType.LAZY)
	private List<CartItem> cartItems;
	
	@CreationTimestamp
	@Column(name="created_at",nullable=false,updatable=false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at",nullable=false)
	private LocalDateTime updatedAt;
	
	public Cart() {}

	public Cart(User user, List<CartItem> cartItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.user = user;
		this.cartItems = cartItems;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	
	
	public Long getCartId() {
		return cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
	    return "Cart [cartId=" + cartId
	            + ", createdAt=" + createdAt
	            + ", updatedAt=" + updatedAt
	            + "]";
	}
	
	
	
}
