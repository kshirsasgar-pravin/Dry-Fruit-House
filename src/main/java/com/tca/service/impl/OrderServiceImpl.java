package com.tca.service.impl;

import java.util.List;

import com.tca.dao.CartDAO;
import com.tca.dao.OrderDAO;
import com.tca.dao.ProductDAO;
import com.tca.dao.impl.CartDAOImpl;
import com.tca.dao.impl.OrderDAOImpl;
import com.tca.dao.impl.ProductDAOImpl;
import com.tca.entity.Cart;
import com.tca.entity.Order;
import com.tca.entity.OrderItem;
import com.tca.entity.Product;
import com.tca.enums.OrderStatus;
import com.tca.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private final OrderDAO orderDAO;
	private final ProductDAO productDAO;
	private final CartDAO cartDAO;

	public OrderServiceImpl() {
		this.orderDAO = new OrderDAOImpl();
		this.productDAO = new ProductDAOImpl();
		this.cartDAO = new CartDAOImpl();
	}

	@Override
	public boolean placeOrder(Order order) {
		if (order == null || order.getUser() == null || order.getOrderItems() == null
				|| order.getOrderItems().isEmpty()) {
			return false;
		}


		for (OrderItem item : order.getOrderItems()) {
			Product product = productDAO.getProductById(item.getProduct().getProductId());

			if (product == null) {
				System.out.println("Order Failed With Product id " + product.getProductId());
				return false;
			}

			if (product.getStockQuantity() < item.getQuantity()) {
				System.out.println("Order Failed due to insufficent quantity for product :" + product.getProductName()
						+ " Available " + product.getStockQuantity() + " Requested :" + item.getQuantity());
				return false;
			}
		}

		if (!orderDAO.saveOrder(order)) {
			System.out.println("Unable to save order in DB ");
			return false;
		}

		for (OrderItem item : order.getOrderItems()) {
			Product orderedProduct = item.getProduct();
			long id = orderedProduct.getProductId();
			Product product = productDAO.getProductById(id);
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
			
			if (!productDAO.updateProduct(product)) {
				System.out.println("Failed to update Quantity for product " + product.getProductName());
				continue;
			}
		}
		long userId = order.getUser().getUserId();
		Cart cart = cartDAO.getCartByUserId(userId);
		Long userCartId = cart.getCartId();

		if (!cartDAO.deleteCart(userCartId)) {
			System.out.println("Failed to remove cart from use id " + userId);
		}

		return true;
	}

	@Override
	public boolean cancleOrder(Long orderId) {
	   
	    if (orderId == null || orderId <= 0) {
	        System.out.println("Invalid Order id provided for cancellation ");
	        return false; 
	    }

	    Order order = orderDAO.getOrderById(orderId);
	    if (order == null) {
	        System.out.println("Order Cancellation Failed due to order not found for order Id " + orderId);
	        return false;
	    }

	    if (order.getOrderStatus() == OrderStatus.CANCELLED) {
	        System.out.println("Order Already Cancelled !!! ");
	        return false;
	    }

	    if (order.getOrderStatus() == OrderStatus.DELIVERED) {
	        System.out.println("Order Can't be cancelled, it's already DELIVERED ");
	        return false;
	    }

	    
	    if (order.getOrderItems() != null) {
	        for (OrderItem item : order.getOrderItems()) {
	            Product product = productDAO.getProductById(item.getProduct().getProductId());
	            if (product != null) {
	                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
	                if (!productDAO.updateProduct(product)) {
	                    System.out.println("Warning : Failed to restore quantity for Product ID: " + product.getProductId());
	                }
	            }
	        }
	    }

	   
	    order.setOrderStatus(OrderStatus.CANCELLED);
	    if (orderDAO.updateOrder(order)) {
	        System.out.println("Order Cancelled Successfully");
	        return true;
	    } else {
	        System.out.println("Failed to update order status in database for Order ID: " + orderId);
	        return false;
	    }
	}
	@Override
	public boolean updateOrder(Order order) {
		if (order == null || order.getOrderId() == null) {
			return false;
		}
		return orderDAO.updateOrder(order);
	}

	@Override
	public Order getOrderById(Long orderId) {
		if (orderId == null)
			return null;
		return orderDAO.getOrderById(orderId);
	}

	@Override
	public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
		if (orderStatus == null)
			return null;
		return orderDAO.getOrdersByStatus(orderStatus);
	}


	public List<Order> getOrdersByUserId(Long userId) {
		if (userId == null)
			return null;
		return orderDAO.getOrdersByUserId(userId);
	}

	public List<Order> getAllOrders() {
		return orderDAO.getAllOrders();
	}

}