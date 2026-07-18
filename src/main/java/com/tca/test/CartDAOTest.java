package com.tca.test;

import com.tca.dao.impl.CartDAOImpl;
import com.tca.dao.impl.CustomerDAOImpl;
import com.tca.entity.Cart;
import com.tca.entity.Customer;

public class CartDAOTest {

	public void testCart() {

		CartDAOImpl cartDAO = new CartDAOImpl();
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		// Fetch existing customer (user_id = 1)
		Customer customer = customerDAO.getCustomerById(1L);

		if (customer == null) {
			System.out.println("Customer not found.");
			return;
		}

		// ================= SAVE CART =================

		System.out.println("\n========== SAVE CART ==========");

		Cart cart = new Cart();
		cart.setCustomer(customer);

		if (cartDAO.saveCart(cart)) {
			System.out.println("Cart saved successfully.");
		} else {
			System.out.println("Failed to save cart.");
			return;
		}

		// ================= UPDATE CART =================

		System.out.println("\n========== UPDATE CART ==========");

		// No editable fields are present in Cart.
		// Calling update only to verify merge() works.
		if (cartDAO.updateCart(cart)) {
			System.out.println("Cart updated successfully.");
		} else {
			System.out.println("Failed to update cart.");
		}

		// ================= GET CART BY ID =================

		System.out.println("\n========== GET CART BY ID ==========");

		Cart cartById = cartDAO.getCartById(cart.getCartId());

		if (cartById != null) {
			System.out.println(cartById);
		} else {
			System.out.println("Cart not found.");
		}

		// ================= GET CART BY USER ID =================

		System.out.println("\n========== GET CART BY USER ID ==========");

		Cart customerCart = cartDAO.getCartByCustomerId(customer.getUserId());
		// If you renamed the method:
		// Cart customerCart = cartDAO.getCartByUserId(customer.getUserId());

		if (customerCart != null) {
			System.out.println(customerCart);
		} else {
			System.out.println("Cart not found.");
		}

		// ================= DELETE CART =================

		System.out.println("\n========== DELETE CART ==========");

		if (cartDAO.deleteCart(cart.getCartId())) {
			System.out.println("Cart deleted successfully.");
		} else {
			System.out.println("Failed to delete cart.");
		}
	}
}