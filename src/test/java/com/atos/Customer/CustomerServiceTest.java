package com.atos.Customer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.atos.customer.main.CustomerApplication;
import com.atos.customer.model.Customer;
import com.atos.customer.service.CustomerService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CustomerApplication.class })
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Test
	public void isValidCustomer() throws Exception {
		Assert.assertFalse(customerService.isValidCustomer(CustomerUtils.createCustomer(null,
				null)));
		Assert.assertFalse(customerService.isValidCustomer(CustomerUtils.createCustomer("Su",
				null)));
		Assert.assertFalse(customerService.isValidCustomer(CustomerUtils.createCustomer(null,
				"Cew")));
		Assert.assertTrue(customerService.isValidCustomer(CustomerUtils.createCustomer("Suma",
				"Hema")));

	}

	// ---------------------------------------------------------------------------------------------------

	@Test
	public void createCustomer() throws Exception {
		Customer customer = CustomerUtils.createCustomer("Su", "Cew");
		Customer retCustomer = customerService.addCustomer(customer);
		Assert.assertNotNull(retCustomer);
		Assert.assertNotNull(retCustomer.getId());
		Assert.assertEquals(customer.getFirstName(), retCustomer.getFirstName());
		Assert.assertEquals(customer.getLastName(), retCustomer.getLastName());
	}

	// ---------------------------------------------------------------------------------------------------

	@Test
	public void getCustomerById() throws Exception {
		Customer customer = CustomerUtils.createCustomer("Su", "Cew");
		Customer cust = customerService.addCustomer(customer);
		Customer retCustomer = customerService.getCustomerById(cust.getId());
		Assert.assertNotNull(retCustomer);
		Assert.assertNotNull(retCustomer.getId());
		Assert.assertEquals(customer.getFirstName(), retCustomer.getFirstName());
		Assert.assertEquals(customer.getLastName(), retCustomer.getLastName());

	}

	// ---------------------------------------------------------------------------------------------------

	@Test
	public void listCustomers() throws Exception {
		//Clear cache
		List<Customer> delcustomerList = customerService.listCustomer();
		for(Customer customer : delcustomerList){
			customerService.deleteCustomerById(customer.getId());
		}
		Customer customer1 = CustomerUtils.createCustomer("Su", "Cew");
		Customer customer2 = CustomerUtils.createCustomer("Tim", "Crook");
		customerService.addCustomer(customer1);
		customerService.addCustomer(customer2);
		List<Customer> customerList = customerService.listCustomer();
		Assert.assertNotNull(customerList);
		Assert.assertEquals(customerList.size(),2);

	}

	// ---------------------------------------------------------------------------------------------------
	
	@Test
	public void deleteCustomerById() throws Exception {
		Customer cust = customerService.addCustomer(CustomerUtils.createCustomer("Su", "Cew"));
		customerService.deleteCustomerById(cust.getId());
		Assert.assertNull(customerService.getCustomerById(cust.getId()));
	}

	// ---------------------------------------------------------------------------------------------------

	
}
