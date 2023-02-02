package com.egt.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private PersonDto person;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String street1;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String street2;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String city;
    private String state;
    private String zipCode;
}
