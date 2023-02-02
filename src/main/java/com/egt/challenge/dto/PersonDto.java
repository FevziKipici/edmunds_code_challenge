package com.egt.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String street1;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String street2;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String city;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String state;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String zipCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AddressDto> additionalAddresses;
}
