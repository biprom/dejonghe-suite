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
public class InvoiceDTO {

    @JsonProperty("OrderType")
    String orderType;

    @JsonProperty("OrderDirection")
    String orderDirection;

    @JsonProperty("OrderNumber")
    String orderNumber;

    @JsonProperty("OrderDate")
    String orderDate;

    @JsonProperty("ExpiryDate")
    String expiryDate;

    @JsonProperty("OrderPDF")
    OrderPDFDTO orderPDFDTO;

    @JsonProperty("Attachments")
    List<OrderPDFDTO> attachementPDFDTO;

    @JsonProperty("Customer")
    CustomerDTO CustomerDTO;

    @JsonProperty("OrderLines")
    List<OrderLinesDTO> orderLinesDTOList;

    @JsonProperty("Reference")
    String poNumber;
}
