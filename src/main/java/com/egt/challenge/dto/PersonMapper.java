package com.egt.challenge.dto;

import com.egt.challenge.model.Person;

public interface PersonMapper {

    PersonDto toDto(Person entity);

    PersonDto toDtoAll(Person entity);

    Person toEntity(PersonDto dto);
}
