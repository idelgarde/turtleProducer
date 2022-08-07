package com.turtle.turtleProducer.service.stub;

import com.turtle.turtleProducer.dto.RegisterRequest;

public class RegisterRequestStubFactory {

  public static RegisterRequest registerRequest() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setRegistrationNumber("2222");
    registerRequest.setUrl("https://localhost/");
    return registerRequest;
  }

}
