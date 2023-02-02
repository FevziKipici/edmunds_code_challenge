package com.egt.challenge.repo;

import com.egt.challenge.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonRepositoryImpl implements PersonRepository {

    private final Map<Long, Person> repo = new HashMap<>();

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return repo.values()
                .stream()
                .filter(p -> p.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId((long) (repo.size() + 1));
        }
        repo.put(person.getId(), person);
        return person;
    }

    @Override
    public void delete(Person person) {
        repo.remove(person.getId());
    }
}
