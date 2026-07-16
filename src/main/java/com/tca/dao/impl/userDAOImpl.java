package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.UserDAO;
import com.tca.entity.User;
import com.tca.enums.UserRole;
import com.tca.util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean saveUser(User user) {

		if (user == null) {
			return false;
		}

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.persist(user);

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
	public boolean updateUser(User user) {
		if (user == null) {
			return false;
		}

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.merge(user);

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
	public boolean deleteUser(Long userId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			User user = session.get(User.class, userId);

			if (user == null) {
				return false;
			}

			session.remove(user);

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
	public User getUserById(Long userId) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			User user = session.get(User.class, userId);

			transaction.commit();

			return user;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
			        FROM User u
			        WHERE u.email = :email
			        """;

			Query<User> query = session.createQuery(hql, User.class);

			query.setParameter("email", email);

			User user = query.uniqueResult();

			transaction.commit();

			return user;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUserByPhone(String phone) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = """
			        FROM User u
			        WHERE u.phone = :phone
			        """;

			Query<User> query = session.createQuery(hql, User.class);

			query.setParameter("phone", phone);

			User user = query.uniqueResult();

			transaction.commit();

			return user;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

	@Override
	public List<User> getAllUsers() {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = "FROM User";

			Query<User> query = session.createQuery(hql, User.class);

			List<User> userList = query.getResultList();

			transaction.commit();

			return userList;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getUsersByRole(UserRole role) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			String hql = "FROM User u WHERE u.userRole = :role";

			Query<User> query = session.createQuery(hql, User.class);

			query.setParameter("role", role);

			List<User> userList = query.getResultList();

			transaction.commit();

			return userList;

		} catch (HibernateException he) {

			if (transaction != null) {
				transaction.rollback();
			}

			he.printStackTrace();
		}

		return null;
	}

}
