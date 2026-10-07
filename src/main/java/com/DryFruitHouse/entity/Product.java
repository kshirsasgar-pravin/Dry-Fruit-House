package com.DryFruitHouse.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name="products")
public class Product {
	
	@Id
	@Column(name="product_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productId;
	
	@Column(name="product_name",nullable=false,length = 150)
	private String productName;
    
	private String description;
	
	@Column(name="weight",nullable=false)
	private BigDecimal weight;
	
	@Column(nullable=false)
	private BigDecimal price;
	
	@Column(name="stock_quantity",nullable=false)
	private Integer stockQuantity;
	
	@Column(name="product_image", nullable=false)
	private String productImage;
	
	@Column(name="is_active",nullable = false)
	private Boolean isActive=true;
	
	@CreationTimestamp
	@Column(name="created_at",nullable=false,updatable=false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at" ,nullable = false)
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	
	public Product() {}

	public Product(String productName, String description, BigDecimal weight, BigDecimal price, Integer stockQuantity,
			String productImage, Category category) {
		super();
		this.productName = productName;
		this.description = description;
		this.weight = weight;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.productImage = productImage;
		this.category = category;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public Integer getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", description=" + description
				+ ", weight=" + weight + ", price=" + price + ", stockQuantity=" + stockQuantity + ", productImage="
				+ productImage + ", isActive=" + isActive + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+"]";
	}
	
	
}
