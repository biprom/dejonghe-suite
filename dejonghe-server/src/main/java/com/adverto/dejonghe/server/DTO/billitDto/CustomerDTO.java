package com.adverto.dejonghe.server.DTO.billitDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

    @JsonProperty("Name")
    String CustomerName;

    @JsonProperty("VATNumber")
    String vatNumber;

    @JsonProperty("PartyType")
    String partyType;

    @JsonProperty("Identifiers")
    List<IdentifiersDTO> identifiersDTOList;

    @JsonProperty("Addresses")
    List<AddressDTO> addressDTOList;

}
