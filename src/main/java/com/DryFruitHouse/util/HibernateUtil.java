 package com.DryFruitHouse.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory=null;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();

            System.out.println("Hibernate SessionFactory created successfully.");
        } catch (Throwable t) {
            System.err.println("Initial SessionFactory creation failed.");
            throw new ExceptionInInitializerError(t);
        }
    }

    private HibernateUtil() {
        // Prevent object creation
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory closed.");
        }
    }
}