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
public class OrderLinesDTO {

    @JsonProperty("Quantity")
    String quantity;

    @JsonProperty("UnitPriceExcl")
    String unitPriceExcl;

    @JsonProperty("Description")
    String description;

    @JsonProperty("VATPercentage")
    String vatPercentage;

}
