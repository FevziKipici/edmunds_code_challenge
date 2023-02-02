package com.egt.challenge.repo;

import com.egt.challenge.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();

    Optional<Person> findById(Long id);

    List<Person> findByLastName(String lastName);

    Person save(Person person);

    void delete(Person person);
}
