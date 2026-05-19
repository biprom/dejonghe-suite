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
public class IdentifiersDTO {
    @JsonProperty("IdentifierType")
    String identifierType;

    @JsonProperty("Identifier")
    String identifier;
}
