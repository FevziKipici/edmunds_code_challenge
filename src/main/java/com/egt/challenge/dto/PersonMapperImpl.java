package com.egt.challenge.dto;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonMapperImpl implements PersonMapper {
    @NonNull
    private final AddressMapper addressMapper;
    @NonNull
    private final DtoUtil dtoUtils;

    @Override
    public PersonDto toDto(Person entity) {
        PersonDto personDto = dtoUtils.buildFullPersonDto(entity);

        // Map additional address to addressDTOs
        if (Objects.nonNull(entity.getAdditionalAddresses())) {
            Set<Address> addressSet = entity.getAdditionalAddresses();
            List<AddressDto> additionalAddresses = dtoUtils.mapAddressSetToAddressDtoList(addressSet);
            personDto.setAdditionalAddresses(additionalAddresses);
        }
        return personDto;
    }

    public PersonDto toDtoAll(Person entity) {
        PersonDto personDto = PersonDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .build();
        return personDto;
    }

    @Override
    public Person toEntity(PersonDto dto) {
        Person person = Person.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .mainAddress(null)
                .additionalAddresses(null)
                .build();

        Address mainAddress = Address.builder()
                .person(person)
                .street1(dto.getStreet1())
                .street2(dto.getStreet2())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();

        person.setMainAddress(mainAddress);

        // null check
        if (Objects.nonNull(dto.getAdditionalAddresses()) && !dto.getAdditionalAddresses().isEmpty()) {
            Set<Address> additionalAddresses = dto.getAdditionalAddresses().stream()
                    .map(addressDto -> {
                        addressDto.setPerson(dto);
                        return addressMapper.toEntity(addressDto);
                    }).collect(Collectors.toSet());
            person.setAdditionalAddresses(additionalAddresses);
        } else {
            person.setAdditionalAddresses(new HashSet<>());
        }

        return person;
    }
}
