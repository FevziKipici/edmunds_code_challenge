package com.egt.challenge.repo;

import com.egt.challenge.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<Address> findAll();

    Optional<Address> findById(Long id);

    Address save(Address address);

    void delete(Address address);
}
