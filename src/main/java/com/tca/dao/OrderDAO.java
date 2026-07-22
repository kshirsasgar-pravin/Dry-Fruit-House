package com.tca.dao;

import java.util.List;

import com.tca.entity.Order;
import com.tca.enums.OrderStatus;

public interface OrderDAO {
     
    boolean saveOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(Long orderId);

    boolean cancelOrder(Long userId, Long orderId);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long userId);
    
    List<Order> getOrdersByStatus(OrderStatus orderStatus);
}