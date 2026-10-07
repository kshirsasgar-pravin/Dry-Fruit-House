package com.DryFruitHouse.repository.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.DryFruitHouse.repository.CategoryDAO;
import com.DryFruitHouse.entity.Category;
import com.DryFruitHouse.util.HibernateUtil;

public class CategoryDAOImpl implements CategoryDAO {

	@Override
	public boolean saveCategory(Category category) {
		Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

	      transaction = session.beginTransaction();

	       session.persist(category);

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
	public boolean updateCategory(Category category) {
		 Transaction transaction = null;
		    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
		      transaction = session.beginTransaction();

		      session.merge(category);

		      transaction.commit();
		      return true;
		    } catch (HibernateException he) {
		      if (transaction != null) {
		        transaction.rollback();
		      }
		      he.printStackTrace();
		      return false;
		    }
	}

	@Override
	public boolean deleteCategory(Long categoryId) {
		Transaction transaction = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	      transaction = session.beginTransaction();

	      Category category = session.get(Category.class, categoryId);

	      if (category != null) {
	        session.remove(category);
	      }
	      transaction.commit();
	      return true;
	      
	    } catch (HibernateException he) {
	      if (transaction != null) {
	        transaction.rollback();
	      }

	      he.printStackTrace();
	      return false;
	    }
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		Transaction transaction = null;
	    Category category = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	      transaction = session.beginTransaction();
	      category = session.get(Category.class, categoryId);
	      transaction.commit();

	    } catch (HibernateException he) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      he.printStackTrace();
	    }

	    return category;
	}

	@Override
	public Category getCategoryByName(String categoryName) {
		Category category = null;
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	      transaction = session.beginTransaction();
	      String hql = "FROM Category WHERE categoryName = :categoryName";
	      Query<Category> query = session.createQuery(hql, Category.class);
	      query.setParameter("categoryName", categoryName);
	      category = query.uniqueResult();
	      transaction.commit();
	      
	    } catch (HibernateException he) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      he.printStackTrace();
	    }
	    return category;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categoryList = null;
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	      transaction = session.beginTransaction();
	      String hql = "FROM Category";
	      Query<Category> query = session.createQuery(hql, Category.class);
	      categoryList = query.getResultList();
	      transaction.commit();
	      
	    } catch (HibernateException he) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      he.printStackTrace();
	    }

	    return categoryList;
	}

	@Override
	public List<Category> getActiveCategories() {
	    List<Category> categoryList = null;
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	      
	      String hql = "FROM Category WHERE isActive = :isActive";
	      transaction = session.beginTransaction();
	      Query<Category> query = session.createQuery(hql, Category.class);
	      query.setParameter("isActive", true);
	      categoryList = query.getResultList();
	      transaction.commit();
	      
	    } catch (HibernateException he) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      he.printStackTrace();
	    }
	    return categoryList;
	  }

	

}
