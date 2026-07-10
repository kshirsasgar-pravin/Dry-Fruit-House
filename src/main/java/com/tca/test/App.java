package com.tca.test;

import org.hibernate.Session;

import com.tca.util.HibernateUtil;

public class App {
	public static void main(String[] args) {
		Session session = null;
		session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Hibernate connectted sucessfully");
        System.out.println("Session opened successfully");
        
        session.close();
        
        System.out.println("Session close successfully");
        HibernateUtil.shutdown();
	}
}
