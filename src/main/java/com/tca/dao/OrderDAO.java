package com.tca.dao;

import java.util.List;

import com.tca.entity.Order;

public interface OrderDAO {
     
	boolean saveOrder(Order order);

	boolean updateOrder(Order order);

	boolean deleteOrder(Long orderId);

	boolean cancelOrder(Long customerId, Long orderId);

	Order getOrderById(Long orderId);

	List<Order> getAllOrders();

	List<Order> getOrdersByCustomerId(Long customerId);
	
}
