package com.DryFruitHouse.repository.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.DryFruitHouse.repository.AddressDAO;
import com.DryFruitHouse.entity.Address;
import com.DryFruitHouse.util.HibernateUtil;

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
	        return query.getResultList();
	    } catch (HibernateException he) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        he.printStackTrace();
	    }
	    return null;
	}

	@Override
	public Address getDefaultAddressByUserId(Long userId) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	       
	        String hql = "FROM Address a WHERE a.user.userId = :userId AND a.isDefault = :isDefault";
	        
	        Query<Address> query = session.createQuery(hql, Address.class);
	        query.setParameter("userId", userId);
	        query.setParameter("isDefault", true);
	        
	        // Safely fetch the single result or null
	        return query.uniqueResultOptional().orElse(null);
	        
	    } catch (HibernateException he) {
	        System.err.println("Error fetching default address for userId: " + userId);
	        he.printStackTrace();
	    }
	    return null;
	}

}
