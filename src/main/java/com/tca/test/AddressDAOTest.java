package com.tca.test;

import java.util.List;

import com.tca.dao.impl.AddressDAOImpl;
import com.tca.dao.impl.UserDAOImpl;
import com.tca.entity.Address;
import com.tca.entity.User;
import com.tca.enums.AddressType;

public class AddressDAOTest {

	public void testAddress() {

		AddressDAOImpl addressDAO = new AddressDAOImpl();
		UserDAOImpl userDAO = new UserDAOImpl();

		// Fetch existing user
		User user = userDAO.getUserById(1L);

		if (user == null) {
			System.out.println("User not found. Please create a user first.");
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
		address.setUser(user);

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

		// ================= GET ADDRESSES BY USER ID =================

		System.out.println("\n========== GET ADDRESSES BY USER ID ==========");

		List<Address> userAddresses = addressDAO.getAddressesByUserId(user.getUserId());

		if (userAddresses != null && !userAddresses.isEmpty()) {
			for (Address a : userAddresses) {
				System.out.println(a);
			}
		} else {
			System.out.println("No addresses found for this user.");
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