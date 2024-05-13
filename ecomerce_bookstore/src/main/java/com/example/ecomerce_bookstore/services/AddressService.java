package com.example.ecomerce_bookstore.services;

import com.example.ecomerce_bookstore.entities.Address;
import com.example.ecomerce_bookstore.payloads.AddressDTO;

import java.util.List;


public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, Address address);

    String deleteAddress(Long addressId);
}
