package com.DryFruitHouse.test;

import com.DryFruitHouse.repository.impl.CartDAOImpl;
import com.DryFruitHouse.repository.impl.UserDAOImpl;
import com.DryFruitHouse.entity.Cart;
import com.DryFruitHouse.entity.User;

public class CartDAOTest {

	public void testCart() {

		CartDAOImpl cartDAO = new CartDAOImpl();
		UserDAOImpl userDAO = new UserDAOImpl();

		// Fetch existing user (user_id = 1)
		User user = userDAO.getUserById(1L);

		if (user == null) {
			System.out.println("User not found.");
			return;
		}

		// ================= SAVE CART =================

		System.out.println("\n========== SAVE CART ==========");

		Cart cart = new Cart();
		cart.setUser(user);

		if (cartDAO.saveCart(cart)) {
			System.out.println("Cart saved successfully.");
		} else {
			System.out.println("Failed to save cart.");
			return;
		}

		// ================= UPDATE CART =================

		System.out.println("\n========== UPDATE CART ==========");

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

		Cart userCart = cartDAO.getCartByUserId(user.getUserId());

		if (userCart != null) {
			System.out.println(userCart);
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