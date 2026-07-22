package com.tca.service;

import java.util.List;

import com.tca.entity.Order;
import com.tca.enums.OrderStatus;

public interface OrderService {
         boolean placeOrder(Order order);
         boolean updateOrder(Order order);
         boolean cancleOrder(Long orderId);
         Order getOrderById(Long orderId);
         List<Order> getOrdersByStatus(OrderStatus orderStatus);
         
         
}
