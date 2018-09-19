package com.atos.customer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atos.customer.model.Customer;

/**
 * Defines the rest api for the customers
 * 
 * @author Vino
 *
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerController.class);

	/**
	 * Create a new customer
	 * 
	 * @param customer
	 *            Customer Object holds the firstname and lastname which are
	 *            mandatory fields
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {

		logger.info("Create Customer {}", customer);
		if (customerService.isValidCustomer(customer)) {
			customerService.addCustomer(customer);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.CONFLICT);
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Lists all customers
	 * 
	 * @return
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Customer>> listCustomers() {
		logger.info("List all Customers");
		return new ResponseEntity<List<Customer>>(
				customerService.listCustomer(), HttpStatus.OK);
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Delete the customer by customer id
	 * 
	 * @param id
	 *            Id of the Customer
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable long id) {
		logger.info("Delete the customer by customer id {}", id);
		Customer customer = customerService.getCustomerById(id);
		if (customer == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		customerService.deleteCustomerById(id);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
