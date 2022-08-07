package com.turtle.turtleProducer.connector;

import com.turtle.turtleProducer.endpoint.PplsApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PesticideConnector {

    private final PplsApiClient pplsApiClient;

    @Autowired
    public PesticideConnector(PplsApiClient pplsApiClient) {
        this.pplsApiClient = pplsApiClient;
    }

    public String getRegistrationProduct(String registrationNumber) {
        log.info("Received get pesticideProductInformation by registration number: {}", registrationNumber);
        String pesticideProduct = pplsApiClient.getPesticideProdInfoByRegistrationNumber(registrationNumber);
        log.info("JSON from registration number: {}, Result: {}", registrationNumber, pesticideProduct);
        return pesticideProduct;
    }
}
