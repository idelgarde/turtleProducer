package com.turtle.turtleProducer.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.turtle.turtleProducer.dto.RegisterRequest;
import com.turtle.turtleProducer.endpoint.PplsApiClient;
import com.turtle.turtleProducer.service.stub.RegisterRequestStubFactory;
import java.nio.charset.Charset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

@SpringBootTest
class ConverterServiceTest {

  @Autowired
  private ConverterService sut;

  @MockBean
  PplsApiClient pplsApiClient;

  @SneakyThrows
  @Test
  void should_execute_request() {
    RegisterRequest registerRequest = RegisterRequestStubFactory.registerRequest();
    when(pplsApiClient.getPesticideProdInfoByRegistrationNumber("2222")).thenReturn(
        StreamUtils.copyToString(new ClassPathResource("/samples/sample.json").getInputStream(), Charset.defaultCharset()));

    String convertedFile = sut.registerData(registerRequest);
    assertThat(!convertedFile.isEmpty());
  }
}