package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.OrderDAO;
import com.tca.entity.Order;
import com.tca.enums.OrderStatus;
import com.tca.util.HibernateUtil;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public boolean saveOrder(Order order) {

		if (order == null) {
			return false;
		}

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.persist(order);

			transaction.commit();

			return true;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateOrder(Order order) {

		if (order == null) {
			return false;
		}

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(order);

			transaction.commit();

			return true;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteOrder(Long orderId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Order order = session.get(Order.class, orderId);

			if (order == null) {
				return false;
			}

			session.remove(order);

			transaction.commit();

			return true;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean cancelOrder(Long userId, Long orderId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
					FROM Order o
					WHERE o.orderId = :orderId
					AND o.customer.userId = :userId
					""";

			Query<Order> query = session.createQuery(hql, Order.class);

			query.setParameter("orderId", orderId);
			query.setParameter("userId", userId);

			Order order = query.uniqueResult();

			if (order == null) {
				return false;
			}

			if (order.getOrderStatus() == OrderStatus.DELIVERED
					|| order.getOrderStatus() == OrderStatus.CANCELLED) {
				return false;
			}

			order.setOrderStatus(OrderStatus.CANCELLED);

			session.merge(order);

			transaction.commit();

			return true;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return false;
	}

	@Override
	public Order getOrderById(Long orderId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Order order = session.get(Order.class, orderId);

			transaction.commit();

			return order;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Order> getAllOrders() {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = "FROM Order";

			Query<Order> query = session.createQuery(hql, Order.class);

			List<Order> orderList = query.getResultList();

			transaction.commit();

			return orderList;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Order> getOrdersByCustomerId(Long userId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
					FROM Order o
					WHERE o.customer.userId = :userId
					""";

			Query<Order> query = session.createQuery(hql, Order.class);

			query.setParameter("userId", userId);

			List<Order> orderList = query.getResultList();

			transaction.commit();

			return orderList;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

}