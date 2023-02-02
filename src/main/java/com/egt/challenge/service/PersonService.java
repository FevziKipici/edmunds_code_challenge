package com.egt.challenge.service;

import java.util.List;

import com.egt.challenge.model.Person;

public interface PersonService {

	/**
	 * it is used to get all person
	 * @return
	 */
	List<Person> findAll();

	/**
	 * it is used to find a Person for given id
	 * @param id
	 * @return
	 */
	Person findById(Long id) ;

	/**
	 * it is used to find a person for a given lastname
	 * @param lastName
	 * @return
	 */
	List<Person> findByLastName(String lastName);

	/**
	 * it is used to save person
	 * @param person
	 * @return
	 */
	Person save(Person person);

	/**
	 * it is used to update a person
	 * @param person
	 * @return
	 */
	Person update(Person person) ;

	/**
	 * it is used to delete a person for a given id
	 * @param id
	 */
	void deleteById  (Long id) ;

}
