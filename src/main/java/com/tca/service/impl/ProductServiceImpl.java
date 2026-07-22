package com.tca.service.impl;

import java.util.List;

import com.tca.dao.impl.ProductDAOImpl;
import com.tca.entity.Product;
import com.tca.service.ProductService;

public class ProductServiceImpl implements ProductService {

	private static ProductDAOImpl productDAO;
	public ProductServiceImpl(ProductDAOImpl productDAO){
		this.productDAO = productDAO;
	}
	
	@Override
	public boolean addProduct(Product product) {
		if(product == null||
		   product.getProductName() == null ||
		   product.getProductName().trim().isEmpty()||
		   productDAO.getProductByName(product.getProductName()) != null) {
			return false;
		}

		return productDAO.saveProduct(product);
	}

	@Override
	public boolean updateProduct(Product product) {
		if(product == null ||
		   productDAO.getProductByName(product.getProductName()) == null) {
			return false;
		}
		
		return productDAO.updateProduct(product);
	}

	@Override
	public boolean deleteProduct(Long productId) {
		if(productDAO.getProductById(productId)==null) return false;
		return productDAO.deleteProduct(productId);
	}

	@Override
	public Product getProductById(Long productId) {

		return productDAO.getProductById(productId);
	}

	@Override
	public Product getProductByName(String productName) {
		
		return productDAO.getProductByName(productName);
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productDAO.getAllProducts();
	}

	@Override
	public List<Product> getProductsByCategory(Long categoryId) {
		
		return productDAO.getProductsByCategoryId(categoryId);
	}

	@Override
	public boolean reduceProductQuantity(Long productId, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

}
