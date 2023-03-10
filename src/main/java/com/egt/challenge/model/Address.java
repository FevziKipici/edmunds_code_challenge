package com.egt.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private Long id;
    
    private Person person;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipCode;
    
    
    
}
