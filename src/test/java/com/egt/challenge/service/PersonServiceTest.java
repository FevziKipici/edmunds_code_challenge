
package com.egt.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
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
class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private AddressRepository addressRepository;

	@InjectMocks
	private PersonServiceImpl personService;

	private List<Person> personList;
	private Person person;
	private Address address;
	private Address address2;
	private Address mainAddress;
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
    public void whenFindAll_thenReturnSortedPersons() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> result = personService.findAll();

        verify(personRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals("Wayne", result.get(0).getLastName());

    }

	@Test
	public void whenFindById_thenReturnPerson() {
		Long id = 1L;
		when(personRepository.findById(id)).thenReturn(Optional.of(person));
		Person result = personService.findById(id);

		verify(personRepository, times(1)).findById(id);
		assertEquals("Bruce", result.getFirstName());
		assertEquals("Wayne", result.getLastName());
	}


	@Test
    public void whenSavePerson_thenReturnSavedPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Person savedPerson = personService.save(person);
        assertEquals(person, savedPerson);
}

	@Test
	  void deleteById_deletesPersonAndTheirAddresses() {
	    Long id = 1L;

	    Optional<Person> optionalPerson = Optional.of(person);
	    when(personRepository.findById(id)).thenReturn(optionalPerson);

	    personService.deleteById(id);

	    verify(personRepository).delete(person);
	    verify(addressRepository).delete(mainAddress);
	    
	    for (Address address : additionalAddress) {
			verify(addressRepository).delete(address);
		}
	  }
	
	@Test
	void whenupdatePerson_Success() {
		Person newPerson = new Person();
		newPerson.setId(1L);
		newPerson.setFirstName("Walter");
		newPerson.setLastName("White");
		newPerson.setMainAddress(address);
		
		Person foundPerson = new Person();
		foundPerson.setId(1L);
		foundPerson.setFirstName("Tony");
		foundPerson.setLastName("Stark");
		foundPerson.setMainAddress(mainAddress);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(foundPerson));
		when(addressRepository.save(address)).thenReturn(address);
		when(personRepository.save(foundPerson)).thenReturn(foundPerson);
		
		Person updatedPerson = personService.update(newPerson);
		
		assertEquals(newPerson.getId(), updatedPerson.getId());
		assertEquals(newPerson.getFirstName(), updatedPerson.getFirstName());
		assertEquals(newPerson.getLastName(), updatedPerson.getLastName());
		assertEquals(newPerson.getMainAddress().getCity(), updatedPerson.getMainAddress().getCity());
		
		verify(personRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).save(address);
		verify(personRepository, times(1)).save(foundPerson);
	}
}