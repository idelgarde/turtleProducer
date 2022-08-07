package com.turtle.turtleProducer.controller;

import com.turtle.turtleProducer.dto.RegisterRequest;
import com.turtle.turtleProducer.service.ConverterService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/turtleProducer")
public class TurtleController {

  private final ConverterService converterService;

  @Autowired
  public TurtleController(ConverterService converterService) {
    this.converterService = converterService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity registerData(@RequestBody @Valid RegisterRequest registerRequest) {
    String turtleXml = converterService.registerData(registerRequest);
    if (turtleXml == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(turtleXml, HttpStatus.CREATED);
  }
}
