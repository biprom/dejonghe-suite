package com.adverto.dejonghe.server.DTO.billitDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    @JsonProperty("AddressType")
    String addressType;

    @JsonProperty("Name")
    String name;

    @JsonProperty("Street")
    String street;

    @JsonProperty("StreetNumber")
    String streetNumber;

    @JsonProperty("City")
    String city;

    @JsonProperty("Zipcode")
    String zipCode;
}
