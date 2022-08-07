package com.turtle.turtleProducer.endpoint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pplsApiClient", url = "${endpoint.ppls.url}", fallbackFactory = PplsApiClientFallbackFactory.class)
public interface PplsApiClient {
    @GetMapping(path = "/ppls/{registrationNumber}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    String getPesticideProdInfoByRegistrationNumber(@PathVariable String registrationNumber);
}
