package com.w3r1.eventLocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class EventLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventLocatorApplication.class, args);
	}
}

