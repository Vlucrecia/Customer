package com.atos.customer.service;

import java.util.List;

import com.atos.customer.model.Customer;

public interface CustomerService {

	public Customer addCustomer(Customer customer);

	public List<Customer> listCustomer();

	public void deleteCustomerById(long customerId);

	public Customer getCustomerById(long customerId);

	public boolean isValidCustomer(Customer customer);
}
