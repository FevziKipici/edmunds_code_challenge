package com.egt.challenge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egt.challenge.dto.PersonDto;
import com.egt.challenge.dto.PersonMapper;
import com.egt.challenge.model.Person;
import com.egt.challenge.service.PersonService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(PersonController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {
	public static final String BASE_URL = "/api/persons";

	@NonNull
	private final PersonService personService;
	@NonNull
	private final PersonMapper personMapper;

	@GetMapping("/person")
	public ResponseEntity<List<PersonDto>> findAll() {
		List<Person> persons = personService.findAll();
		List<PersonDto> dtoList = persons.stream().map(personMapper::toDtoAll).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("/person/{id}")
	public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
		Person person = personService.findById(id);
		PersonDto personDto = personMapper.toDto(person);
		return ResponseEntity.ok(personDto);
	}

	@PostMapping("/person/{lastName}")
	public ResponseEntity<List<PersonDto>> findByLastName(@PathVariable String lastName) {
		List<Person> persons = personService.findByLastName(lastName);
		List<PersonDto> dtoList = persons.stream().map(p -> personMapper.toDto(p)).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@PostMapping("/person")
	public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto) {
		Person person = personMapper.toEntity(personDto);
		Person savedPerson = personService.save(person);
		PersonDto dto = personMapper.toDto(savedPerson);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@DeleteMapping("/person/{id}")
	public ResponseEntity<Map<String,String>> delete(@PathVariable Long id) {
		personService.deleteById(id);
		Map<String,String> map=new HashMap<>();
		map.put("success", "true");
		map.put("message", "Person deleted successfully");
		return  ResponseEntity.ok(map);
	}

	@PutMapping("/person")
	public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) {
		Person person = personMapper.toEntity(personDto);
		Person savedPerson = personService.update(person);
		PersonDto dto = personMapper.toDto(savedPerson);
		return  ResponseEntity.ok(dto);
	}

}
