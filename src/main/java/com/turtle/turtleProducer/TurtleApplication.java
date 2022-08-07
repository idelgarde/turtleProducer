package com.turtle.turtleProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@CrossOrigin
@EnableFeignClients
public class TurtleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurtleApplication.class, args);
	}


}
