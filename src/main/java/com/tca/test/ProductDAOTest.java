package com.tca.test;

import java.math.BigDecimal;
import java.util.List;

import com.tca.dao.impl.CategoryDAOImpl;
import com.tca.dao.impl.ProductDAOImpl;
import com.tca.entity.Category;
import com.tca.entity.Product;

public class ProductDAOTest {

    public void testProduct() {

        ProductDAOImpl productDAO = new ProductDAOImpl();
        CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

        // ================= SAVE PRODUCT =================

        Category category = categoryDAO.getCategoryById(2L);

        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        Product product = new Product();

        product.setProductName("Premium Cashew");
        product.setDescription("Premium quality W320 Cashew");
        product.setWeight("1 KG");
        product.setPrice(new BigDecimal("850.00"));
        product.setStockQuantity(100);
        product.setProductImage("cashew.jpg");
        product.setCategory(category);
        product.setIsActive(true);

        if (productDAO.saveProduct(product)) {
            System.out.println("Product added successfully.");
        }

        // ================= UPDATE PRODUCT =================

        product.setProductName("Premium Almond");
        product.setDescription("Premium California Almond");
        product.setWeight("500 GM");
        product.setPrice(new BigDecimal("650.00"));
        product.setStockQuantity(80);
        product.setProductImage("almond.jpg");
        product.setIsActive(true);

        if (productDAO.updateProduct(product)) {
            System.out.println("Product updated successfully.");
        }

        // ================= GET PRODUCT BY ID =================

        product = productDAO.getProductById(product.getProductId());

        System.out.println("\nProduct By Id");
        System.out.println(product);

        // ================= GET PRODUCT BY NAME =================

        product = productDAO.getProductByName("Premium Almond");

        System.out.println("\nProduct By Name");
        System.out.println(product);

        // ================= GET ALL PRODUCTS =================

        List<Product> productList = productDAO.getAllProducts();

        System.out.println("\nAll Products");

        for (Product p : productList) {
            System.out.println(p);
        }

        // ================= GET ACTIVE PRODUCTS =================

        List<Product> activeProducts = productDAO.getActiveProducts();

        System.out.println("\nActive Products");

        for (Product p : activeProducts) {
            System.out.println(p);
        }

        // ================= GET PRODUCTS BY CATEGORY =================

        List<Product> categoryProducts =
                productDAO.getProductsByCategoryId(category.getCategoryId());

        System.out.println("\nProducts By Category");

        for (Product p : categoryProducts) {
            System.out.println(p);
        }

        // ================= SEARCH PRODUCT =================

        List<Product> searchedProducts =
                productDAO.searchProducts("Almond");

        System.out.println("\nSearch Result");

        for (Product p : searchedProducts) {
            System.out.println(p);
        }

        // ================= DELETE PRODUCT =================
//
//        if (productDAO.deleteProduct(product.getProductId())) {
//            System.out.println("\nProduct deleted successfully.");
//        }

    }

}