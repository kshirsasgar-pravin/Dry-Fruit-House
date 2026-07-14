package com.tca.dao;

import java.util.List;

import com.tca.entity.Customer;

public interface CustomerDAO {
      
	boolean saveCustomer(Customer customer);

	boolean updateCustomer(Customer customer);

	boolean deleteCustomer(Long customerId);

	Customer getCustomerById(Long customerId);

	Customer getCustomerByPhone(String phone);

	List<Customer> getAllCustomers();

	List<Customer> getActiveCustomers();
	
}
