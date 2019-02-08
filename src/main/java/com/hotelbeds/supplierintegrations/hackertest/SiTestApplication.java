package com.hotelbeds.supplierintegrations.hackertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hotelbeds.supplierintegrations.hackertest"})
public class SiTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiTestApplication.class, args);
	}

}

