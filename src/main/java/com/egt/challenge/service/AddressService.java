package com.egt.challenge.service;

import java.util.List;

import com.egt.challenge.model.Address;

public interface AddressService {

	/**
	 * it is used to get all addresses
	 * @return
	 */
	List<Address> findAll();

	/**
	 * it is used to find an address for a given id
	 * @param id
	 * @return
	 */
	Address findById(Long id);

	/**
	 * it is used to save an address
	 * @param address
	 * @return
	 */
	Address save(Address address);
	
	/**
	 * it is used to update an address
	 * @param address
	 * @return
	 */
	Address update(Address address);

	/**
	 * it is used to delete an address for a given id
	 * @param id
	 */
	void deleteById(Long id);
}
