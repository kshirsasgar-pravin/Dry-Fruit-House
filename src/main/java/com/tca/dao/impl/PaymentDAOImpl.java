package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.PaymentDAO;
import com.tca.entity.Payment;
import com.tca.util.HibernateUtil;

public class PaymentDAOImpl implements PaymentDAO {

	@Override
	public boolean savePayment(Payment payment) {
		Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    	if (payment == null) {
	    	    return false;
	    	}
	      transaction = session.beginTransaction();

	       session.persist(payment);

	      transaction.commit();
	      return true;

	    } catch (HibernateException e) {
	      if (transaction != null)        
	    	  {
	    	     transaction.rollback();
	    	  }
	      e.printStackTrace();
	      
	    }
		return false;
	}

	@Override
	public boolean updatePayment(Payment payment) {
		if (payment == null) {
			return false;
		}

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(payment);

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
	public boolean deletePayment(Long paymentId) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Payment payment = session.get(Payment.class, paymentId);

			if (payment == null) {
				return false;
			}

			session.remove(payment);

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
	public Payment getPaymentById(Long paymentId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			Payment payment = session.get(Payment.class, paymentId);

			transaction.commit();

			return payment;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}
		return null;
	}

	@Override
	public Payment getPaymentByOrderId(Long orderId) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
					FROM Payment p
					WHERE p.order.orderId = :orderId
					""";

			Query<Payment> query = session.createQuery(hql, Payment.class);

			query.setParameter("orderId", orderId);

			Payment payment = query.uniqueResult();

			transaction.commit();

			return payment;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public Payment getPaymentByTransactionId(String transactionId) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
					FROM Payment p
					WHERE p.transactionId = :transactionId
					""";

			Query<Payment> query = session.createQuery(hql, Payment.class);

			query.setParameter("transactionId", transactionId);

			Payment payment = query.uniqueResult();

			transaction.commit();

			return payment;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Payment> getAllPayments() {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = "FROM Payment";

			Query<Payment> query = session.createQuery(hql, Payment.class);

			List<Payment> paymentList = query.getResultList();

			transaction.commit();

			return paymentList;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Payment> getPaymentsByUserId(Long userId) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        String hql = """
	                FROM Payment p
	                WHERE p.order.user.userId = :userId
	                """;
	        Query<Payment> query = session.createQuery(hql, Payment.class);
	        query.setParameter("userId", userId);
	        List<Payment> paymentList = query.getResultList();
	        transaction.commit();
	        return paymentList;
	    } catch (HibernateException he) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        he.printStackTrace();
	    }
	    return null;
	}

}
