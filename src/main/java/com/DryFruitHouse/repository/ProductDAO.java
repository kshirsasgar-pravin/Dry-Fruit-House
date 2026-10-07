package com.DryFruitHouse.repository;

import java.util.List;

import com.DryFruitHouse.entity.Product;

public interface ProductDAO {
	boolean saveProduct(Product product);

	boolean updateProduct(Product product);

	boolean deleteProduct(Long productId);

	Product getProductById(Long productId);

	Product getProductByName(String productName);

	List<Product> getAllProducts();

	List<Product> getActiveProducts();

	List<Product> getProductsByCategoryId(Long categoryId);

	List<Product> searchProducts(String keyword);
}
