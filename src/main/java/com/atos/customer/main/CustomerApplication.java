package com.atos.customer.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application for the customer
 * 
 * @author Vino
 *
 */
@ComponentScan("com.atos.customer")
@SpringBootApplication
public class CustomerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class);
	}
}
