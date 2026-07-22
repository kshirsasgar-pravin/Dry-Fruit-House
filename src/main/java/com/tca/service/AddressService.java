package com.tca.service;

import java.util.List;
import com.tca.entity.Address;

public interface AddressService {

    boolean addAddress(Address address);

    boolean updateAddress(Address address);

    boolean deleteAddress(Long addressId);

    Address getAddressById(Long addressId);

    List<Address> getAddressesByUserId(Long userId);

    Address getDefaultAddressByUserId(Long userId);

    boolean setDefaultAddress(Long addressId, Long userId);
}