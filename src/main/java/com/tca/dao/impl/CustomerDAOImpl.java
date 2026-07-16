package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.CustomerDAO;
import com.tca.entity.Customer;
import com.tca.util.HibernateUtil;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public boolean saveCustomer(Customer customer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			if (customer == null) {
				return false;
			}
			transaction = session.beginTransaction();
			session.persist(customer);

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
	public boolean updateCustomer(Customer customer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			if (customer == null) {
				return false;
			}
			transaction = session.beginTransaction();
			session.merge(customer);
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
	public boolean deleteCustomer(Long customerId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Customer customer = session.get(Customer.class, customerId);
			if (customer == null) {
				return false;
			}

			session.remove(customer);
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
	public Customer getCustomerById(Long customerId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Customer customer = session.get(Customer.class, customerId);
			transaction.commit();
			return customer;
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer getCustomerByPhone(String phone) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String hql = "FROM Customer WHERE phone = :phone";
			Query<Customer> query = session.createQuery(hql, Customer.class);
			query.setParameter("phone", phone);
			Customer customer = query.uniqueResult();
			transaction.commit();
				return customer;
		    
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String hql = "FROM Customer";
			Query<Customer> query = session.createQuery(hql, Customer.class);
			List<Customer> customerList = query.getResultList();
			transaction.commit();
			return customerList;
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Customer> getActiveCustomers() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String hql = "FROM Customer WHERE isActive = :isActive";
			Query<Customer> query = session.createQuery(hql, Customer.class);
			query.setParameter("isActive", true);
			List<Customer> customerList = query.getResultList();
			transaction.commit();
			return customerList;
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}

		return null;
	}

}
