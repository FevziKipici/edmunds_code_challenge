package com.egt.challenge.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.egt.challenge.exception.BadRequestException;
import com.egt.challenge.exception.ResourceNotFoundException;
import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.repo.AddressRepository;
import com.egt.challenge.repo.PersonRepository;
import com.egt.challenge.util.Validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
	@NonNull
	private final AddressRepository addressRepository;

	@NonNull
	private final PersonRepository personRepository;

	/**
	 * It is used to validate address fields by using methods is @see
	 * com.egt.challegence.util.Validator class
	 * 
	 * @param address
	 */
	private void validateAddressFields(Address address) {
		if (!Validator.validateStringValue(address.getZipCode())) {
			throw new BadRequestException("Please provide a zipcode");
		}

		if (!Validator.validateStringValue(address.getState())) {
			throw new BadRequestException("Please provide a state");
		}
	}

	/**
	 * it is used to get all person
	 */
	@Override
	public List<Address> findAll() {
		List<Address> allAddress = addressRepository.findAll();

		// if these fields are null put empty string to compare
		allAddress.stream().forEach(address -> {
			if (Objects.isNull(address.getStreet1()))
				address.setStreet1("");
			if (Objects.isNull(address.getState()))
				address.setState("");
		});

		// compare firstly based on street1 then street2
		return allAddress.stream().sorted(Comparator.comparing(Address::getStreet1).thenComparing(Address::getState))
				.collect(Collectors.toList());
	}

	@Override
	public Address findById(Long id) {
		// find a address and return, if not found throw a ResourceNotFoundException
		return addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Address with id %d not found.", id)));
	}

	@Override
	public Address save(Address address) {

		//validate address fields
		validateAddressFields(address);

		//get person
		Person foundPerson = personRepository.findById(address.getPerson().getId()).orElseThrow(
				() -> new ResourceNotFoundException("Person not found with id:" + address.getPerson().getId()));

		//save address
		Address savedAddress = addressRepository.save(address);

		
		
		Set<Address> additionalAddresses = foundPerson.getAdditionalAddresses();

		//add additional address to address set of a person
		additionalAddresses.add(savedAddress);

		foundPerson.setAdditionalAddresses(additionalAddresses);

		//save person to save additional address of a person
		personRepository.save(foundPerson);

		return savedAddress;
	}

	@Override
	public void deleteById(Long id) {

		if (Objects.isNull(id))
			throw new BadRequestException("Address id cannot be null");

		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Address nor found with id:" + id));

		// get all person
		List<Person> allPerson = personRepository.findAll();
		
	    //filter if match address id with any main address	
		List<Person> filteredList = allPerson.stream()
				.filter(p -> p.getMainAddress().getId().longValue() == id.longValue()).collect(Collectors.toList());
    
		//if filtered list is not empty, it is main address so we can't delete
		if (!filteredList.isEmpty()) {
			throw new BadRequestException("This address is a main address. it can't be deleted");
		}
		//

		addressRepository.delete(address);

		Person person = personRepository.findById(address.getPerson().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Person not found with id:" + id));
		

		List<Address> previousAddressList = new ArrayList<>(person.getAdditionalAddresses().stream().toList());

		
		//find index of address that will be removed
        int index=-1;
		for (int i = 0; i < previousAddressList.size(); i++) {
			if (previousAddressList.get(i).getId() == id) {
				index=i;
				break;
			}
		}
		
		//if found remove it
		if(index>-1) {
			previousAddressList.remove(index);
		}

		//set updated addressList to person
		Set<Address> addressSet = previousAddressList.stream().collect(Collectors.toSet());
		person.setAdditionalAddresses(addressSet);

		personRepository.save(person);
	}

	@Override
	public Address update(Address newAddress) {
		Long addressId = newAddress.getId();

		if (Objects.isNull(addressId))
			throw new BadRequestException("Address id cannot be null");

		if (Objects.isNull(newAddress.getPerson())) {
			throw new BadRequestException("Person in address  cannot be null");
		}

		Long personId = newAddress.getPerson().getId();

		if (Objects.isNull(newAddress.getPerson().getId())) {
			throw new BadRequestException("Please provide person id");
		}

		validateAddressFields(newAddress);

		Address foundAddress = addressRepository.findById(newAddress.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id:" + addressId));

		Person foundPerson = personRepository.findById(newAddress.getPerson().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Person not found with id:" + personId));

		ServiceUtil.updateAddressFields(newAddress, foundAddress);

		// update person'main address
		if (foundPerson.getMainAddress().getId().longValue() == newAddress.getId().longValue()) {
			foundPerson.setMainAddress(newAddress);
		}

		List<Address> previousAddressList = new ArrayList<>(foundPerson.getAdditionalAddresses().stream().toList());

		addressRepository.save(newAddress);

		
		//find additional address and update with new Address
		for (int i = 0; i < previousAddressList.size(); i++) {
			if (previousAddressList.get(i).getId() == newAddress.getId()) {
				previousAddressList.set(i, newAddress);
				break;
			}
		}

		
		Set<Address> addressSet = previousAddressList.stream().collect(Collectors.toSet());
		
		//set updated additional address to a person 
		foundPerson.setAdditionalAddresses(addressSet);
		newAddress.setPerson(foundPerson);
		newAddress = addressRepository.save(newAddress);

		//save person
		personRepository.save(foundPerson);
		return newAddress;
	}
}