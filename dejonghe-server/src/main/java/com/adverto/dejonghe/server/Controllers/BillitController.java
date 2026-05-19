package com.adverto.dejonghe.server.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class BillitController {

    @Value("${billit.api.key}")
    private String billitApiKey;

    @Value("${billit.party.id}")
    private String billitPartyId;

    private final RestTemplate restTemplate;

    public BillitController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String sendOrder(String invoiceBody, String encodedPdf) {

        String url = "https://api.billit.be/v1/orders";
        //String url = "https://api.sandbox.billit.be/v1/orders";

        // make Headers
        HttpHeaders headers = new HttpHeaders();

        //sandbox
        //headers.set("ApiKey", "b4cb39fc-a064-4084-899f-cc8df1b2f328");

        //real Billit stuff
        headers.set("ApiKey", billitApiKey);
        headers.set("partyId", billitPartyId);

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = invoiceBody.formatted(encodedPdf);


        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getBody();
    }
}
