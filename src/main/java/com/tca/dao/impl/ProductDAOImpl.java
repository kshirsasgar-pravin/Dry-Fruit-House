package com.tca.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tca.dao.ProductDAO;
import com.tca.entity.Product;
import com.tca.util.HibernateUtil;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public boolean saveProduct(Product product) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			
			session.persist(product);
			
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
	public boolean updateProduct(Product product) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
		    session.merge(product);
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
	public boolean deleteProduct(Long productId) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			Product product = session.get(Product.class, productId);
			if (product != null) {
			    session.remove(product);
			} else {
			    return false;
			}
			transaction.commit();
			return true;
		}
		catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();		}
		return false;
	}

	@Override
	public Product getProductById(Long productId) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			Product product = session.get(Product.class, productId);
		    transaction.commit();
		    return product;
		}
		catch(HibernateException he){
		     if(transaction != null) {	
		    	  transaction.rollback();
		     }
		     he.printStackTrace();
		}
		return null;
	}

	@Override
	public Product getProductByName(String productName) {
		Transaction transaction = null;
	    try(Session session = HibernateUtil.getSessionFactory().openSession()){
	    	    transaction = session.beginTransaction();
	    	    String hql = "FROM Product WHERE productName = :productName";
	    	    Query<Product> query= session.createQuery(hql,Product.class);
	    	    query.setParameter("productName", productName);
	    	    Product product = query.uniqueResult();
	    	    transaction.commit();
	    	    return product;
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
	public List<Product> getAllProducts() {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			String hql = "FROM Product";
			Query<Product> query = session.createQuery(hql,Product.class);
			List<Product> productList = query.getResultList();
			transaction.commit();
		    return productList;
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
	public List<Product> getActiveProducts() {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			String hql = "FROM Product WHERE isActive = :isActive";
			Query<Product> query = session.createQuery(hql,Product.class);
			query.setParameter("isActive", true);
			List<Product> productList = query.getResultList();
			transaction.commit();
		    return productList;
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
	public List<Product> getProductsByCategoryId(Long categoryId) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			String hql = "FROM Product WHERE category.categoryId = :categoryId";
			Query<Product> query = session.createQuery(hql,Product.class);
			query.setParameter("categoryId", categoryId);
			List<Product> productList = query.getResultList();
			transaction.commit();
		    return productList;
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
	public List<Product> searchProducts(String keyword) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			String hql = "FROM Product WHERE isActive = true "
					+"AND LOWER(productName) LIKE LOWER(:keyword)";
			Query<Product> query = session.createQuery(hql,Product.class);
			query.setParameter("keyword", "%" + keyword + "%");
			List<Product> productList = query.getResultList();
			transaction.commit();
		    return productList;
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
