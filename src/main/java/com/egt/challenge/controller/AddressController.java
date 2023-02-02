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

import com.egt.challenge.dto.AddressDto;
import com.egt.challenge.dto.AddressMapper;
import com.egt.challenge.model.Address;
import com.egt.challenge.service.AddressService;
import com.egt.challenge.service.PersonService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(AddressController.BASE_URL)
@RequiredArgsConstructor
public class AddressController {
    public static final String BASE_URL = "/api/addresses";

    @NonNull
    private final AddressService addressService;
    
    @NonNull
    private final PersonService personService;
    
    @NonNull
    private final AddressMapper addressMapper;

    // TODO create the appropriate endpoints as outlined in the README
    @GetMapping("/address")
    public ResponseEntity<List<AddressDto>> findAll() {
        List<Address> addresses = addressService.findAll();
        List<AddressDto> dtoList = addresses.stream().map(address -> addressMapper.toDtoAll(address))
				.collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable Long id) {
        Address address = addressService.findById(id);
        AddressDto addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDto> save(@RequestBody AddressDto addressDto) {
    	Address address = addressMapper.toEntity(addressDto);
        Address savedAddress=addressService.save(address);
        AddressDto dto = addressMapper.toDto(savedAddress);
        return ResponseEntity.ok(dto);
    }
    
	 @PutMapping("/address")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto) {
		Address address= addressMapper.toEntity(addressDto);
		Address updatedAddress=addressService.update(address);
		AddressDto dto=addressMapper.toDto(updatedAddress);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id) {
        addressService.deleteById(id);
 		Map<String,String> map=new HashMap<>();
 		map.put("success", "true");
 		map.put("message", "Address deleted successfully");
 		return  ResponseEntity.ok(map);
    }
}
