package com.atos.customer.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.atos.customer.model.Customer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Service class to persist the customer data to a cache
 * 
 * @author Vino
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	// Cache is used to persist data for a short duration
	private static LoadingCache<Long, Customer> customerCache;

	// ---------------------------------------------------------------------------------------------------

	@PostConstruct
	public void init() {
		customerCache = CacheBuilder.newBuilder().maximumSize(500)
				.expireAfterAccess(1, TimeUnit.HOURS)
				.build(new CacheLoader<Long, Customer>() {
					@Override
					public Customer load(final Long customerId)
							throws Exception {
						return getCustomerById(customerId.longValue());
					}
				});
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Adds a customer to the cache
	 * 
	 * @param customer
	 *            Given Customer Object
	 */
	public Customer addCustomer(Customer customer) {
		customer.setId(new Random().nextInt(Integer.MAX_VALUE));
		customerCache.put(customer.getId(), customer);
		return customer;

	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Lists all the customer from the cache
	 */

	public List<Customer> listCustomer() {
		return customerCache.asMap().entrySet().stream().map(x -> x.getValue())
				.collect(Collectors.toList());
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Delete the customer by customer Id
	 * 
	 * @param customer
	 *            Given Customer Object
	 */
	public void deleteCustomerById(long customerId) {
		customerCache.invalidate(Long.valueOf(customerId));
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Validates the customer Object. Firstname and lastname are mandatory
	 * fields
	 * 
	 * @param customer
	 *            Given Customer Object
	 */
	public boolean isValidCustomer(Customer customer) {
		if ((StringUtils.isBlank(customer.getFirstName()))
				|| (StringUtils.isBlank(customer.getLastName()))) {
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Get the customer object by customer Id
	 * 
	 * @param customerId
	 *            Given Customer id
	 */
	public Customer getCustomerById(long customerId) {
		try {
			return customerCache.get(Long.valueOf(customerId));
		} catch (Exception e) {
			return null;
		}
	}
}
