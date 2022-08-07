package com.turtle.turtleProducer.service;


import com.turtle.turtleProducer.connector.PesticideConnector;
import com.turtle.turtleProducer.dto.RegisterRequest;
import com.turtle.turtleProducer.helper.ConvertJsonToTurtleHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConverterService {

  private final PesticideConnector pesticideConnector;

  public ConverterService(PesticideConnector pesticideConnector) {
    this.pesticideConnector = pesticideConnector;
  }

  @SneakyThrows
  public String registerData(RegisterRequest registerRequest) {
    String pesticideProductInfo = pesticideConnector.getRegistrationProduct(registerRequest.getRegistrationNumber());

    ConvertJsonToTurtleHelper convertJsonToTurtleHelper = new ConvertJsonToTurtleHelper(
            new ByteArrayInputStream(pesticideProductInfo.getBytes()),
            new ByteArrayOutputStream(1024),
            registerRequest.getUrl());
    return convertJsonToTurtleHelper.convert();
  }
}
