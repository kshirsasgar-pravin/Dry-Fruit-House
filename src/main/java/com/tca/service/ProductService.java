package com.tca.service;

import java.util.List;

import com.tca.entity.Product;

public interface ProductService {

	boolean addProduct(Product product);

	boolean updateProduct(Product product);

	boolean deleteProduct(Long productId);

	Product getProductById(Long productId);

	Product getProductByName(String productName);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(Long categoryId);

	boolean reduceProductQuantity(Long productId, int quantity);

}
