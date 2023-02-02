
package com.egt.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.repo.AddressRepository;
import com.egt.challenge.repo.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@Mock
	private AddressRepository addressRepository;
	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private AddressServiceImpl addressService;

	private Person person;
	private Address address;
	private Address address2;
	private Address mainAddress;
	private List<Person> personList;
	private Set<Address> additionalAddress;

	@BeforeEach
	void setUp() {
		person = new Person(1L, "Bruce", "Wayne", LocalDate.now().minusDays(5), null, null);

		address = new Address();
		address.setId(1L);
		address.setState("NY");
		address.setCity("Gotham");
		address.setZipCode("066652");
		address.setStreet1("Street1");

		address2 = new Address();
		address2.setId(2L);
		address2.setState("Washinton");
		address2.setCity("Apple");
		address2.setZipCode("034586");
		address2.setStreet1("Street2");
		address2.setPerson(person);

		additionalAddress = new HashSet<>();
		additionalAddress.add(address);
		additionalAddress.add(address2);

		person.setAdditionalAddresses(additionalAddress);
		personList = new ArrayList<>();

		address.setPerson(person);
		address2.setPerson(person);

		mainAddress = new Address();
		mainAddress.setId(3L);
		mainAddress.setState("Azriona");
		mainAddress.setCity("Peach");
		mainAddress.setZipCode("38065");
		mainAddress.setStreet1("mainStreet");
		mainAddress.setPerson(person);

		person.setMainAddress(mainAddress);
		personList.add(person);
	}

	@Test
	public void testFindAll() {
		Address address1 = new Address();
		address1.setId(1L);
		address1.setStreet1("street1");
		address1.setState("state1");
		Address address2 = new Address();
		address2.setId(2L);
		address2.setStreet1("street2");
		address2.setState("state2");
		List<Address> addresses = Arrays.asList(address1, address2);
		when(addressRepository.findAll()).thenReturn(addresses);

		List<Address> result = addressService.findAll();

		assertThat(result).isEqualTo(addresses);
		verify(addressRepository).findAll();
	}

	@Test
	public void testFindById_Success() {
		Long addressId = 1L;
		Address address = new Address();
		address.setId(addressId);
		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

		Address result = addressService.findById(addressId);

		assertThat(result).isEqualTo(address);
		verify(addressRepository).findById(addressId);
	}

	@Test
	public void testSave_Success() {
		when(personRepository.findById(address.getPerson().getId())).thenReturn(Optional.of(person));
		when(addressRepository.save(address)).thenReturn(address);
		when(personRepository.save(person)).thenReturn(person);

		Address savedAddress = addressService.save(address);

		assertEquals(address, savedAddress);
	}

	@Test
	public void testDeleteById_AddressFound() {
		Long addressId = 1L;

		when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
		when(personRepository.findAll()).thenReturn(Collections.emptyList());
		when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

		addressService.deleteById(addressId);

		verify(addressRepository).delete(address);
		verify(personRepository).save(person);
	}

	@Test
	public void testUpdateAddress_withValidInput_returnsUpdatedAddress() {
		Address newAddress = new Address();
		newAddress.setId(1L);
		newAddress.setState("California");
		newAddress.setCity("Gotham");
		newAddress.setZipCode("066652");
		newAddress.setStreet1("Street1");
		newAddress.setPerson(person);
		
		when(addressRepository.save(newAddress)).thenReturn(newAddress);

		when(addressRepository.findById(1L)).thenReturn(Optional.of(newAddress));
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		Address result = addressService.update(newAddress);
		assertEquals(newAddress, result);
		verify(personRepository, times(1)).save(person);
	}

}
