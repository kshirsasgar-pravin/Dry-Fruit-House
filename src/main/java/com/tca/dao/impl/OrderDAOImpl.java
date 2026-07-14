package com.tca.dao.impl;

import java.util.List;

import com.tca.dao.OrderDAO;
import com.tca.entity.Order;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public boolean saveOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOrder(Long orderId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelOrder(Long customerId, Long orderId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrdersByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
