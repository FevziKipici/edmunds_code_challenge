package com.egt.challenge.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
public class PersonServiceImpl implements PersonService {
	@NonNull
	private final PersonRepository personRepository;

	@NonNull
	private final AddressRepository addressRepository;

	/**
	 * validate values 
	 * @param isUpdate if it is true, it is used to update a person
	 * @param person
	 */
	private void validatePersonFields(boolean isUpdate, Person person) {
		
		// validate values
		if (!Validator.validateStringValue(person.getFirstName())) {
			throw new BadRequestException("Please provide a firstname");
		}

		if (!Validator.validateStringValue(person.getLastName())) {
			throw new BadRequestException("Please provide a lastname");
		}

		//while updating don't check birthdate
		if (!isUpdate) {

			if (!Validator.validateBirthdate(person.getBirthDate())) {
				throw new BadRequestException("Please provide a correct birthdate");
			}
		}

		if (!Validator.validateStringValue(person.getMainAddress().getCity())) {
			throw new BadRequestException("Please provide the city of the address");
		}

		if (!Validator.validateStringValue(person.getMainAddress().getZipCode())) {
			throw new BadRequestException("Please provide the zipcode of the address");
		}
	}

	@Override
	public List<Person> findAll() {
		List<Person> persons = personRepository.findAll();
		
		//sort by getLastName and firstname
		return persons.stream().sorted(Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName))
				.collect(Collectors.toList());
	}

	@Override
	public Person findById(Long id) {
		
		if (Objects.isNull(id))
			throw new BadRequestException("Person id cannot be null");
		
		//find a person and return, if not found throw a ResourceNotFoundException
		return personRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format("Person with id %d not found.", id)));
		
	}

	@Override
	public List<Person> findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	@Override
	public Person save(Person person) {

		// validate values
		validatePersonFields(false,person);

		person = personRepository.save(person);
		// Save all additional addresses
		for (Address address : person.getAdditionalAddresses()) {
			address.getPerson().setId(person.getId());
			addressRepository.save(address);
		}

		// Save main address
		addressRepository.save(person.getMainAddress());

		return personRepository.save(person);

	}

	@Override
	public void deleteById(Long id) {
		
		//get person
		Person person=findById(id);
		
		//delete person
		personRepository.delete(person);
		
		//get main address
		Address mainAddress = person.getMainAddress();
		
		//delete main address
		addressRepository.delete(mainAddress);
		
		//delete all additional addresses
		person.getAdditionalAddresses().forEach(address -> addressRepository.delete(address));
	}

	@Override
	public Person update(Person newPerson) {
		// Check if id given
		if (Objects.isNull(newPerson.getId()))
			throw new BadRequestException("Person id is null");

		// Validate birth date field
		if (Objects.nonNull(newPerson.getBirthDate()))
			throw new BadRequestException("Birth date cannot be changed");

		//validate person fields
		validatePersonFields(true,newPerson);

		//get person
		Person foundPerson = findById(newPerson.getId());

		//update firstname and lastname
		if (Objects.nonNull(newPerson.getFirstName()))
			foundPerson.setFirstName(newPerson.getFirstName());
		if (Objects.nonNull(newPerson.getLastName()))
			foundPerson.setLastName(newPerson.getLastName());

		Address foundAddress = foundPerson.getMainAddress();

		Address newAddress = newPerson.getMainAddress();

		
		//update main address fields
	    ServiceUtil.updateAddressFields(newAddress, foundAddress);


		
		Address updatedAddress = addressRepository.save(newAddress);


		foundPerson.setMainAddress(updatedAddress);

		return personRepository.save(foundPerson);

	}
}
