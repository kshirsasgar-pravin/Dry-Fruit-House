package com.tca.test;

import java.util.List;

import com.tca.dao.impl.CustomerDAOImpl;
import com.tca.entity.Customer;
import com.tca.enums.AuthProvider;
import com.tca.enums.UserRole;

public class CustomerDAOTest {

	public void testCustomer() {

		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		// Generate unique email and phone for every test run
	

		// ================= SAVE CUSTOMER =================

		System.out.println("\n========== SAVE CUSTOMER ==========");

		Customer customer = new Customer();

		// User table fields
		customer.setEmail("customer1@gmail.com");
		customer.setPassword("customer123");
		customer.setPhone("9876543210");
		customer.setUserRole(UserRole.CUSTOMER);
		customer.setAuthProvider(AuthProvider.LOCAL);
		customer.setProviderUserId(null);
		customer.setIsVerified(true);
		customer.setIsActive(true);

		// Customer table fields
		customer.setFullName("Pravin Kshirsagar");
		customer.setProfileImage("profile.jpg");
		customer.setLoyaltyPoints(100);

		if (customerDAO.saveCustomer(customer)) {
			System.out.println("Customer saved successfully.");
		} else {
			System.out.println("Failed to save customer.");
			return;
		}

		// ================= UPDATE CUSTOMER =================

		System.out.println("\n========== UPDATE CUSTOMER ==========");

		customer.setFullName("Rahul Sharma");
		customer.setProfileImage("rahul.jpg");
		customer.setLoyaltyPoints(250);

		if (customerDAO.updateCustomer(customer)) {
			System.out.println("Customer updated successfully.");
		} else {
			System.out.println("Failed to update customer.");
		}

		// ================= GET CUSTOMER BY ID =================

		System.out.println("\n========== GET CUSTOMER BY ID ==========");

		Customer customerById = customerDAO.getCustomerById(customer.getUserId());

		if (customerById != null) {
			System.out.println(customerById);
		} else {
			System.out.println("Customer not found.");
		}

		// ================= GET CUSTOMER BY PHONE =================

		System.out.println("\n========== GET CUSTOMER BY PHONE ==========");

		Customer customerByPhone = customerDAO.getCustomerByPhone(customer.getPhone());

		if (customerByPhone != null) {
			System.out.println(customerByPhone);
		} else {
			System.out.println("Customer not found.");
		}

		// ================= GET ALL CUSTOMERS =================

		System.out.println("\n========== GET ALL CUSTOMERS ==========");

		List<Customer> customerList = customerDAO.getAllCustomers();

		if (customerList != null && !customerList.isEmpty()) {

			for (Customer c : customerList) {
				System.out.println(c);
			}

		} else {
			System.out.println("No customers found.");
		}

		// ================= GET ACTIVE CUSTOMERS =================

		System.out.println("\n========== GET ACTIVE CUSTOMERS ==========");

		List<Customer> activeCustomers = customerDAO.getActiveCustomers();

		if (activeCustomers != null && !activeCustomers.isEmpty()) {

			for (Customer c : activeCustomers) {
				System.out.println(c);
			}

		} else {
			System.out.println("No active customers found.");
		}

		// ================= DELETE CUSTOMER =================
//
//		System.out.println("\n========== DELETE CUSTOMER ==========");
//
//		if (customerDAO.deleteCustomer(customer.getUserId())) {
//			System.out.println("Customer deleted successfully.");
//		} else {
//			System.out.println("Failed to delete customer.");
//		}

	}
}