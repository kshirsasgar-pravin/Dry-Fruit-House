package com.tca.test;

import java.util.List;

import com.tca.dao.impl.AddressDAOImpl;
import com.tca.dao.impl.CustomerDAOImpl;
import com.tca.entity.Address;
import com.tca.entity.Customer;
import com.tca.enums.AddressType;

public class AddressDAOTest {

	public void testAddress() {

		AddressDAOImpl addressDAO = new AddressDAOImpl();
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		// Fetch existing customer
		Customer customer = customerDAO.getCustomerById(1L);

		if (customer == null) {
			System.out.println("Customer not found. Please create a customer first.");
			return;
		}

		// ================= SAVE ADDRESS =================

		System.out.println("\n========== SAVE ADDRESS ==========");

		Address address = new Address();

		address.setAddressType(AddressType.HOME);
		address.setReceiverName("Pravin Kshirsagar");
		address.setCity("Pune");
		address.setAddressLine1("Tathawade");
		address.setAddressLine2("Near JSPM College");
		address.setPhone("9876543210");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setPostalCode("411033");
		address.setIsDefault(true);
		address.setCustomer(customer);

		if (addressDAO.saveAddress(address)) {
			System.out.println("Address saved successfully.");
		} else {
			System.out.println("Failed to save address.");
			return;
		}

		// ================= UPDATE ADDRESS =================

		System.out.println("\n========== UPDATE ADDRESS ==========");

		address.setCity("Mumbai");
		address.setAddressLine2("Near Railway Station");

		if (addressDAO.updateAddress(address)) {
			System.out.println("Address updated successfully.");
		} else {
			System.out.println("Failed to update address.");
		}

		// ================= GET ADDRESS BY ID =================

		System.out.println("\n========== GET ADDRESS BY ID ==========");

		Address addressById = addressDAO.getAddressById(address.getAddressId());

		if (addressById != null) {
			System.out.println(addressById);
		} else {
			System.out.println("Address not found.");
		}

		// ================= GET ALL ADDRESSES =================

		System.out.println("\n========== GET ALL ADDRESSES ==========");

		List<Address> addressList = addressDAO.getAllAddresses();

		if (addressList != null && !addressList.isEmpty()) {
			for (Address a : addressList) {
				System.out.println(a);
			}
		} else {
			System.out.println("No addresses found.");
		}

		// ================= GET ADDRESSES BY CUSTOMER ID =================

		System.out.println("\n========== GET ADDRESSES BY CUSTOMER ID ==========");

		List<Address> customerAddresses = addressDAO.getAddressesByCustomerId(customer.getUserId());

		if (customerAddresses != null && !customerAddresses.isEmpty()) {
			for (Address a : customerAddresses) {
				System.out.println(a);
			}
		} else {
			System.out.println("No addresses found for this customer.");
		}

		// ================= DELETE ADDRESS =================

		System.out.println("\n========== DELETE ADDRESS ==========");

		if (addressDAO.deleteAddress(address.getAddressId())) {
			System.out.println("Address deleted successfully.");
		} else {
			System.out.println("Failed to delete address.");
		}
	}
}