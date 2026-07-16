package com.tca.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.CartDAO;
import com.tca.entity.Cart;
import com.tca.util.HibernateUtil;

public class CartDAOImpl implements CartDAO {

	@Override
	public boolean saveCart(Cart cart) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			if(cart == null) {
				return false;
			}
			transaction = session.beginTransaction();
            session.persist(cart);
            transaction.commit();
            return true;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCart(Cart cart) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			if(cart == null) {
				return false;
			}
			transaction = session.beginTransaction();
            session.merge(cart);
            transaction.commit();
            return true;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCart(Long cartId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
            Cart cart = session.get(Cart.class, cartId);
            if(cart == null) {
            	return false;
            }
            session.remove(cart);
            transaction.commit();
            return true;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}

	@Override
	public Cart getCartById(Long cartId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
            Cart cart = session.get(Cart.class, cartId);
            transaction.commit();
            return cart;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public Cart getCartByCustomerId(Long customerId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String hql = "FROM Cart WHERE customer.customerId = :customerId";
            Query<Cart> query = session.createQuery(hql,Cart.class);
            query.setParameter("customerId", customerId);
			Cart cart = query.uniqueResult();
            transaction.commit();
            return cart;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}
}
