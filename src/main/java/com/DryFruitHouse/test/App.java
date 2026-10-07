package com.DryFruitHouse.test;

import org.hibernate.Session;

import com.DryFruitHouse.util.HibernateUtil;

public class App {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			System.out.println("========================================");
			System.out.println(" Hibernate Connected Successfully");
			System.out.println("========================================");

			// ==============================================
			// CATEGORY DAO TEST
			// ==============================================
			// CategoryDAOTest categoryTest = new CategoryDAOTest();
			// categoryTest.saveCategoryTest();

			// ==============================================
			// PRODUCT DAO TEST
			// ==============================================
			// ProductDAOTest productTest = new ProductDAOTest();
			// productTest.testProduct();

			// USER DAO TEST
			UserDAOTest userTest = new UserDAOTest();
			userTest.saveUserTest();

			// ADDRESS DAO TEST
			AddressDAOTest addressTest = new AddressDAOTest();
			addressTest.testAddress();

			// CART DAO TEST
			CartDAOTest cartTest = new CartDAOTest();
			cartTest.testCart();

			// ORDER DAO TEST
			OrderDAOTest orderTest = new OrderDAOTest();
			orderTest.testOrder();

			// PAYMENT DAO TEST
			PaymentDAOTest paymentTest = new PaymentDAOTest();
			paymentTest.testPayment();

			HibernateUtil.shutdown();
			System.out.println("SessionFactory Closed Successfully.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}