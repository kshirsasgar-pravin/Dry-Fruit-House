package com.DryFruitHouse.repository;

import java.util.List;

import com.DryFruitHouse.entity.Order;
import com.DryFruitHouse.enums.OrderStatus;

public interface OrderDAO {
     
    boolean saveOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(Long orderId);

    boolean cancelOrder(Long userId, Long orderId);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long userId);
    
    List<Order> getOrdersByStatus(OrderStatus orderStatus);

    Order getOrderByOrderIdAndUserId(Long orderId , Long userId);
}