package com.egt.challenge.repo;

import com.egt.challenge.model.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AddressRepositoryImpl implements AddressRepository {

    private final Map<Long, Address> repo = new HashMap<>();

    @Override
    public List<Address> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public Address save(Address address) {
        if (address.getId() == null) {
            address.setId((long) repo.size() + 1);
        }
        repo.put(address.getId(), address);
        return address;
    }

    @Override
    public void delete(Address address) {
        repo.remove(address.getId());
    }
}
