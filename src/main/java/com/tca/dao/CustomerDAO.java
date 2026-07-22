package com.tca.dao;

import java.util.List;

import com.tca.entity.User;

public interface CustomerDAO {
      
	boolean saveCustomer(User customer);

	boolean updateCustomer(User customer);

	boolean deleteCustomer(Long customerId);

	User getCustomerById(Long customerId);

	User getCustomerByPhone(String phone);

	List<User> getAllCustomers();

	List<User> getActiveCustomers();
	
}
