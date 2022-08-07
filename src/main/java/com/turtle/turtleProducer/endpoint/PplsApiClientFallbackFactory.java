package com.turtle.turtleProducer.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class PplsApiClientFallbackFactory implements FallbackFactory<PplsApiClient> {
    @Override
    public PplsApiClient create(Throwable cause) {
        log.error("An error has been happened while connecting to the CommandsApi", cause.getMessage());
        return new PplsApiClient() {
            
            @Override
            public String getPesticideProdInfoByRegistrationNumber(String registrationNumber) {
                return null;
            }
        };
    }
}
