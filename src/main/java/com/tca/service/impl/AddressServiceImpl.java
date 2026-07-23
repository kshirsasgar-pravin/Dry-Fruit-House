package com.tca.service.impl;

import java.util.Collections;
import java.util.List;

import com.tca.dao.AddressDAO;
import com.tca.entity.Address;
import com.tca.service.AddressService;

public class AddressServiceImpl implements AddressService {

	private AddressDAO addressDAO;

	public AddressServiceImpl(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public boolean addAddress(Address address) {
		if (address == null) {
			System.out.println("Address can't be null");
			return false;
		}

		if (address.getUser() == null || address.getUser().getUserId() == null) {
			System.out.println("Add Address Failed : Address must be Associated with the valied User Id");
			return false;
		}

		if (address.getReceiverName() == null || address.getReceiverName().trim().isEmpty()
				|| address.getPhone() == null || address.getPhone().trim().isEmpty() || address.getPostalCode() == null
				|| address.getPostalCode().trim().isEmpty() || address.getState() == null
				|| address.getState().trim().isEmpty() || address.getAddressLine1() == null
				|| address.getAddressLine1().trim().isEmpty() || address.getCity() == null
				|| address.getCity().trim().isEmpty() || address.getCountry() == null) {
			System.out.println("Add Address Failed : Address must contain every information ");
			return false;
		}
		
		List<Address> existingAddress = addressDAO.getAddressesByUserId(address.getUser().getUserId());
		if(existingAddress == null || existingAddress.isEmpty()) {
			address.setIsDefault(true);
		}

		return addressDAO.saveAddress(address);
	}

	@Override
	public boolean updateAddress(Address address) {
		if(address == null || address.getAddressId() == null || address.getAddressId() <= 0) {
			System.out.println("update Address failed : Invalid addressId or address ");
		}
        
		Address existingAddress = addressDAO.getAddressById(address.getAddressId());
		if(existingAddress == null) {
			System.out.println("Update address Failed : Address not found");
		}
		
		if(address.getReceiverName() == null || address.getReceiverName().trim().isEmpty() 
				|| address.getPhone() == null ||address.getPhone().trim().isEmpty()
				|| address.getState() == null || address.getState().trim().isEmpty()
				|| address.getPostalCode() == null || address.getPostalCode().trim().isEmpty()
				|| address.getCity() == null || address.getCity().trim().isEmpty()
				|| address.getAddressLine1() == null || address.getAddressLine1().trim().isEmpty()){
		        	System.out.println("Update Address Failed : Insufficient data , Please provide all information");
		        	return false;
		}
		return addressDAO.updateAddress(address);
	}

	@Override
	public boolean deleteAddress(Long addressId) {
		if (addressId == null || addressId <= 0) {
			System.out.println("Delete Address Failed: Invalid Address ID provided.");
			return false;
		}

		Address existingAddress = addressDAO.getAddressById(addressId);
		if (existingAddress == null) {
			System.out.println("Delete Address Failed: Address with ID " + addressId + " does not exist.");
			return false;
		}

		return addressDAO.deleteAddress(addressId);
	}

	@Override
	public Address getAddressById(Long addressId) {
		if (addressId == null || addressId <= 0) {
			System.out.println("Fetch Address Failed: Invalid Address ID provided.");
			return null;
		}

		return addressDAO.getAddressById(addressId);
	}

	@Override
	public List<Address> getAddressesByUserId(Long userId) {
		if (userId == null || userId <= 0) {
			System.out.println("Fetch Addresses Failed: Invalid User ID provided.");
			return Collections.emptyList();
		}

		List<Address> addresses = addressDAO.getAddressesByUserId(userId);
		return (addresses != null) ? addresses : Collections.emptyList();
	}

	@Override
	public Address getDefaultAddressByUserId(Long userId) {
		if (userId == null || userId <= 0) {
			System.out.println("Fetch Default Address Failed: Invalid User ID provided.");
			return null;
		}

		if(addressDAO.getDefaultAddressByUserId(userId) == null) {
			System.out.println("No such Address found for the userId "+userId);
			return null;
		}
		return addressDAO.getDefaultAddressByUserId(userId);
	}

	@Override
	public boolean setDefaultAddress(Long addressId, Long userId) {
		if (addressId == null || addressId <= 0 || userId == null || userId <= 0) {
			System.out.println("Set Default Address Failed: Invalid Address ID or User ID.");
			return false;
		}

		List<Address> userAddresses = addressDAO.getAddressesByUserId(userId);
		if (userAddresses == null || userAddresses.isEmpty()) {
			System.out.println("Set Default Address Failed: No addresses found for User ID: " + userId);
			return false;
		}

		boolean addressExistsForUser = false;

		for (Address addr : userAddresses) {
			if (addr.getAddressId().equals(addressId)) {
				addr.setIsDefault(true);
				addressExistsForUser = true;
			} else {
				addr.setIsDefault(false);
			}
			addressDAO.updateAddress(addr);
		}

		if (!addressExistsForUser) {
			System.out.println(
					"Set Default Address Failed: Address ID " + addressId + " does not belong to User ID " + userId);
			return false;
		}

		System.out.println("Address ID " + addressId + " successfully set as default address.");
		return true;
	}
}