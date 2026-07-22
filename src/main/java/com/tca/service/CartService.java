package com.tca.service;

import com.tca.entity.Cart;

public interface CartService {

    boolean addToCart(Long userId, Long productId, int quantity);

    boolean removeFromCart(Long userId, Long productId);

    boolean updateQuantity(Long userId, Long productId, int quantity);

    boolean clearCart(Long userId);

    Cart getCartByUserId(Long userId);
}