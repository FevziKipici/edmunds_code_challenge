package com.egt.challenge.dto;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressMapperImpl implements AddressMapper {
    @NonNull
    private final DtoUtil dtoUtils;

    @Override
    public AddressDto toDto(Address entity) {
        Person entityPerson = entity.getPerson();

        PersonDto personDto = dtoUtils.buildFullPersonDto(entityPerson);

        if (Objects.nonNull(entityPerson.getAdditionalAddresses())) {
            Set<Address> addressSet = entityPerson.getAdditionalAddresses();
            List<AddressDto> additionalAddresses = dtoUtils.mapAddressSetToAddressDtoList(addressSet);
            personDto.setAdditionalAddresses(additionalAddresses);
        }

        AddressDto addressDto = AddressDto.builder()
                .id(entity.getId())
                .person(personDto)
                .street1(entity.getStreet1())
                .street2(entity.getStreet2())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .build();

        return addressDto;
    }

    public AddressDto toDtoAll(Address entity) {
        AddressDto addressDto = AddressDto.builder()
                .id(entity.getId())
                .street1(entity.getStreet1())
                .street2(entity.getStreet2())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .build();

        return addressDto;
    }

    @Override
    public Address toEntity(AddressDto dto) {
        Person person = null;

        if (Objects.nonNull(dto.getPerson())) {
            PersonDto personDto = dto.getPerson();

            person = Person.builder()
                    .id(personDto.getId())
                    .build();
        }

        Address address = Address.builder()
                .id(dto.getId())
                .person(person)
                .street1(dto.getStreet1())
                .street2(dto.getStreet2())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();

        return address;
    }
}
