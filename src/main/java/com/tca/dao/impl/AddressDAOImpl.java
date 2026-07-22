package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.AddressDAO;
import com.tca.entity.Address;
import com.tca.util.HibernateUtil;

public class AddressDAOImpl implements AddressDAO {

	@Override
	public boolean saveAddress(Address address) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			if (address == null) {
				return false;
			}
			transaction = session.beginTransaction();
			session.persist(address);

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
	public boolean updateAddress(Address address) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			if (address == null) {
				return false;
			}
			transaction = session.beginTransaction();
			session.merge(address);
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
	public boolean deleteAddress(Long addressId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Address address = session.get(Address.class, addressId);
			if (address == null) {
				return false;
			}

			session.remove(address);
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
	public Address getAddressById(Long addressId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Address address = session.get(Address.class, addressId);
			transaction.commit();

			return address;

		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Address> getAllAddresses() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String hql = "FROM Address";
			Query<Address> query = session.createQuery(hql, Address.class);
			List<Address> addressList = query.getResultList();
			transaction.commit();
			return addressList;
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Address> getAddressesByUserId(Long userId) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        String hql = "FROM Address WHERE user.userId = :userId";
	        Query<Address> query = session.createQuery(hql, Address.class);
	        query.setParameter("userId", userId);
	        List<Address> addressList = query.getResultList();
	        transaction.commit();
	        return addressList;
	    } catch (HibernateException he) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        he.printStackTrace();
	    }
	    return null;
	}

	@Override
	public Address getDefaultAddressById(Long addressId) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			String hql = "FROM Address WHERE addressId =:addressId AND isDefauld =:isDefauld";
			Query<Address> query = session.createQuery(hql,Address.class);
			query.setParameter("addressId", addressId);
			query.setParameter("isDefault", true);
			Address address = query.uniqueResult();
			transaction.commit();
			return address;
			
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
