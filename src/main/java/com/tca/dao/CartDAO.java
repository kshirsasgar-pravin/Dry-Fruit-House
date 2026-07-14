package com.tca.dao;

import com.tca.entity.Cart;

public interface CartDAO {
        
	boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);

    boolean deleteCart(Long cartId);

    Cart getCartById(Long cartId);

    Cart getCartByCustomerId(Long customerId);
}
