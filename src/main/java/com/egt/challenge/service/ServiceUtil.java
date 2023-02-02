package com.egt.challenge.service;

import java.util.Objects;

import com.egt.challenge.model.Address;

public class ServiceUtil {
	
	public static void updateAddressFields(Address newAddress, Address foundAddress) {
		newAddress.setId(foundAddress.getId());

		if (Objects.isNull(newAddress.getCity()) && Objects.nonNull(foundAddress.getCity()))
			newAddress.setCity(foundAddress.getCity());
		if (Objects.isNull(newAddress.getStreet1()) && Objects.nonNull(foundAddress.getStreet1()))
			newAddress.setStreet1(foundAddress.getStreet1());
		if (Objects.isNull(newAddress.getStreet2()) && Objects.nonNull(foundAddress.getStreet2()))
			newAddress.setStreet2(foundAddress.getStreet2());
		if (Objects.isNull(newAddress.getState()) && Objects.nonNull(foundAddress.getState()))
			newAddress.setState(foundAddress.getState());
		if (Objects.isNull(newAddress.getZipCode()) && Objects.nonNull(foundAddress.getZipCode()))
			newAddress.setZipCode(foundAddress.getZipCode());
		
	}

}
