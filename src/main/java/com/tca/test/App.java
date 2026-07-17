package com.tca.test;

import org.hibernate.Session;

import com.tca.util.HibernateUtil;

public class App {
	public static void main(String[] args) {
		Session session = null;
		session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Hibernate connectted sucessfully");
        System.out.println("Session opened successfully");
        CategoryDAOTest categoryTest = new CategoryDAOTest();
        categoryTest.saveCategoryTest();
        categoryTest.updateCategoryTest(2L );
        categoryTest.getCategoryById(3L);
        categoryTest.getCategoryByName("Primum Dry Fruits");
        categoryTest.deleteCategoryTest(1L);
        
        session.close();
        System.out.println("Session close successfully");
        HibernateUtil.shutdown();
	}
}
