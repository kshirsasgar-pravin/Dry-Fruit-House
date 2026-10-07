package com.DryFruitHouse.service;

import java.util.List;

import com.DryFruitHouse.entity.Order;
import com.DryFruitHouse.enums.OrderStatus;

public interface OrderService {
         boolean placeOrder(Order order);
         boolean updateOrder(Order order);
         boolean cancleOrder(Long orderId);
         Order getOrderById(Long orderId);
         List<Order> getOrdersByStatus(OrderStatus orderStatus);
         List<Order> getOrdersByUserId(Long userId);
         List<Order> getAllOrders();
         Order getOrderByOrderIdAndUserId(Long orderId,Long userId);

         
}
