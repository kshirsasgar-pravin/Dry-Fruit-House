package com.tca.test;

import java.math.BigDecimal;
import java.util.List;

import com.tca.dao.impl.CustomerDAOImpl;
import com.tca.dao.impl.OrderDAOImpl;
import com.tca.entity.Customer;
import com.tca.entity.Order;
import com.tca.enums.OrderStatus;

public class OrderDAOTest {

	public void testOrder() {

		OrderDAOImpl orderDAO = new OrderDAOImpl();
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		// Fetch existing customer (user_id = 1)
		Customer customer = customerDAO.getCustomerById(1L);

		if (customer == null) {
			System.out.println("Customer not found.");
			return;
		}

		// ================= SAVE ORDER =================

		System.out.println("\n========== SAVE ORDER ==========");

		Order order = new Order();
		order.setCustomer(customer);
		order.setTotalAmount(new BigDecimal("1250.00"));
		order.setOrderStatus(OrderStatus.PENDING);

		if (orderDAO.saveOrder(order)) {
			System.out.println("Order saved successfully.");
		} else {
			System.out.println("Failed to save order.");
			return;
		}

		// ================= UPDATE ORDER =================

		System.out.println("\n========== UPDATE ORDER ==========");

		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setTotalAmount(new BigDecimal("1500.00"));

		if (orderDAO.updateOrder(order)) {
			System.out.println("Order updated successfully.");
		} else {
			System.out.println("Failed to update order.");
		}

		// ================= GET ORDER BY ID =================

		System.out.println("\n========== GET ORDER BY ID ==========");

		Order orderById = orderDAO.getOrderById(order.getOrderId());

		if (orderById != null) {
			System.out.println(orderById);
		} else {
			System.out.println("Order not found.");
		}

		// ================= GET ALL ORDERS =================

		System.out.println("\n========== GET ALL ORDERS ==========");

		List<Order> orderList = orderDAO.getAllOrders();

		if (orderList != null && !orderList.isEmpty()) {
			for (Order o : orderList) {
				System.out.println(o);
			}
		} else {
			System.out.println("No orders found.");
		}

		// ================= GET ORDERS BY USER ID =================

		System.out.println("\n========== GET ORDERS BY USER ID ==========");

		List<Order> customerOrders = orderDAO.getOrdersByCustomerId(customer.getUserId());
		// If you rename the DAO method later:
		// orderDAO.getOrdersByUserId(customer.getUserId());

		if (customerOrders != null && !customerOrders.isEmpty()) {
			for (Order o : customerOrders) {
				System.out.println(o);
			}
		} else {
			System.out.println("No orders found.");
		}

		// ================= CANCEL ORDER =================

		System.out.println("\n========== CANCEL ORDER ==========");

		if (orderDAO.cancelOrder(customer.getUserId(), order.getOrderId())) {
			System.out.println("Order cancelled successfully.");
		} else {
			System.out.println("Failed to cancel order.");
		}

		// ================= DELETE ORDER =================

		System.out.println("\n========== DELETE ORDER ==========");

		if (orderDAO.deleteOrder(order.getOrderId())) {
			System.out.println("Order deleted successfully.");
		} else {
			System.out.println("Failed to delete order.");
		}
	}
}