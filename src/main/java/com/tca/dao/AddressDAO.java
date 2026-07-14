package com.tca.dao;

import java.util.List;

import com.tca.entity.Address;

public interface AddressDAO {
  
	    boolean saveAddress(Address address);

	    boolean updateAddress(Address address);

	    boolean deleteAddress(Long addressId);

	    Address getAddressById(Long addressId);

	    List<Address> getAllAddresses();

	    List<Address> getAddressesByCustomerId(Long customerId);

	}
