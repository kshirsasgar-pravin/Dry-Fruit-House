package com.DryFruitHouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import com.DryFruitHouse.repository.impl.CartDAOImpl;
import com.DryFruitHouse.repository.impl.ProductDAOImpl;
import com.DryFruitHouse.repository.impl.UserDAOImpl;
import com.DryFruitHouse.entity.Cart;
import com.DryFruitHouse.entity.CartItem;
import com.DryFruitHouse.entity.Product;
import com.DryFruitHouse.entity.User;
import com.DryFruitHouse.service.CartService;

public class CartServiceImpl implements CartService {

    private final CartDAOImpl cartDAO = new CartDAOImpl();
    private final ProductDAOImpl productDAO = new ProductDAOImpl();
    private final UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public boolean addToCart(Long userId, Long productId, Integer quantity) {

        // Validate input
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return false;
        }

        // Validate user
        User user = userDAO.getUserById(userId);

        if (user == null) {
            return false;
        }

        // Validate product
        Product product = productDAO.getProductById(productId);

        if (product == null) {
            return false;
        }

        // Validate stock
        if (product.getStockQuantity() == null ||
                product.getStockQuantity() < quantity) {
            return false;
        }

        // Get existing cart
        Cart cart = cartDAO.getCartByUserId(userId);

        // Create new cart if user doesn't have one
        if (cart == null) {

            cart = new Cart();

            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());

        } else if (cart.getCartItems() == null) {

            cart.setCartItems(new ArrayList<>());
        }

        // Check whether product already exists in cart
        CartItem existingItem = null;

        for (CartItem item : cart.getCartItems()) {

            if (item.getProduct() != null &&
                    item.getProduct().getProductId().equals(productId)) {

                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {

            // Calculate new quantity
            int newQuantity = existingItem.getQuantity() + quantity;

            // Validate total quantity against stock
            if (product.getStockQuantity() < newQuantity) {
                return false;
            }

            existingItem.setQuantity(newQuantity);

            existingItem.setUnitPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(newQuantity));

            existingItem.setSubtotal(subtotal);

        } else {

            // Create new CartItem
            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));

            CartItem cartItem = new CartItem(
                    cart,
                    product,
                    quantity,
                    product.getPrice(),
                    subtotal
            );

            cart.getCartItems().add(cartItem);
        }

        // Save new cart
        if (cart.getCartId() == null) {
            return cartDAO.saveCart(cart);
        }

        // Update existing cart
        return cartDAO.updateCart(cart);
    }

    @Override
    public boolean removeFromCart(Long userId, Long productId) {

        if (userId == null || productId == null) {
            return false;
        }

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null || cart.getCartItems() == null) {
            return false;
        }

        Iterator<CartItem> iterator = cart.getCartItems().iterator();

        while (iterator.hasNext()) {

            CartItem item = iterator.next();

            if (item.getProduct() != null &&
                    item.getProduct().getProductId().equals(productId)) {

                iterator.remove();

                return cartDAO.updateCart(cart);
            }
        }

        return false;
    }

    @Override
    public boolean updateQuantity(Long userId, Long productId, Integer quantity) {

        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            return false;
        }

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null || cart.getCartItems() == null) {
            return false;
        }

        Product product = productDAO.getProductById(productId);

        if (product == null) {
            return false;
        }

        // Check stock
        if (product.getStockQuantity() == null ||
                product.getStockQuantity() < quantity) {

            return false;
        }

        for (CartItem item : cart.getCartItems()) {

            if (item.getProduct() != null &&
                    item.getProduct().getProductId().equals(productId)) {

                item.setQuantity(quantity);

                item.setUnitPrice(product.getPrice());

                BigDecimal subtotal = product.getPrice()
                        .multiply(BigDecimal.valueOf(quantity));

                item.setSubtotal(subtotal);

                return cartDAO.updateCart(cart);
            }
        }

        return false;
    }

    @Override
    public boolean clearCart(Long userId) {

        if (userId == null) {
            return false;
        }

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {
            return false;
        }

        if (cart.getCartItems() != null) {
            cart.getCartItems().clear();
        }

        return cartDAO.updateCart(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {

        if (userId == null) {
            return null;
        }

        return cartDAO.getCartByUserId(userId);
    }
}