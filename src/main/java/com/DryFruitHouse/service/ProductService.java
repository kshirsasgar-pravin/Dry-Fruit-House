package com.DryFruitHouse.service;

import java.util.List;

import com.DryFruitHouse.entity.Product;

public interface ProductService {

	boolean addProduct(Product product);

	boolean updateProduct(Product product);

	boolean deleteProduct(Long productId);

	Product getProductById(Long productId);

	Product getProductByName(String productName);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(Long categoryId);

	List<Product> getActiveProducts();

	boolean reduceProductQuantity(Long productId, int quantity);

}
