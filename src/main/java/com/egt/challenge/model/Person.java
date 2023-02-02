package com.egt.challenge.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude={"mainAddress","additionalAddresses"})
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address mainAddress;
    private Set<Address> additionalAddresses;
}
