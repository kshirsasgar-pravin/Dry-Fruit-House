package com.DryFruitHouse.service;

import com.DryFruitHouse.entity.Cart;

public interface CartService {

    boolean addToCart(Long userId, Long productId, Integer quantity);

    boolean removeFromCart(Long userId, Long productId);

    boolean updateQuantity(Long userId, Long productId, Integer quantity);

    boolean clearCart(Long userId);

    Cart getCartByUserId(Long userId);
}