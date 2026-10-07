package com.DryFruitHouse.repository;

import com.DryFruitHouse.entity.Cart;

public interface CartDAO {
        
    boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);

    boolean deleteCart(Long cartId);

    
    
    Cart getCartById(Long cartId);

    Cart getCartByUserId(Long userId);
    
}