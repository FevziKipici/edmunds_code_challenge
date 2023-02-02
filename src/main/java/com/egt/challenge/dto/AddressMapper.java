package com.egt.challenge.dto;

import com.egt.challenge.model.Address;

public interface AddressMapper {

    AddressDto toDto(Address entity);

    AddressDto toDtoAll(Address entity);

    Address toEntity(AddressDto dto);

}
