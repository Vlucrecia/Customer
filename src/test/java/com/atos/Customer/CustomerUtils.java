package com.atos.Customer;

import com.atos.customer.model.Customer;
/**
 * Utils class to create methods required for testing
 * @author user
 *
 */
public class CustomerUtils {
	public static Customer createCustomer(String firstName, String lastName) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		return customer;
	}
}
