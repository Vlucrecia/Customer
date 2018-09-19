package com.atos.Customer;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.atos.customer.main.CustomerApplication;
import com.atos.customer.model.Customer;
import com.atos.customer.service.CustomerController;
import com.atos.customer.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = { CustomerApplication.class })
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;
	
	private static String CUSTOMER_API_URI = "/api/customers";

	@Test
	public void createInvalidCustomer() throws Exception {
		Mockito.when(
				customerService.isValidCustomer(Mockito.any(Customer.class)))
				.thenReturn(false);
		// Send course as body to /customer
		String customerJson = "{\"firstName\":\"mkyong\",\"lastName\":\"abc\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CUSTOMER_API_URI).accept(MediaType.APPLICATION_JSON)
				.content(customerJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());

	}
	// ---------------------------------------------------------------------------------------------------

	@Test
	public void createValidCustomer() throws Exception {
		Mockito.when(customerService.addCustomer(Mockito.any(Customer.class)))
				.thenReturn(CustomerUtils.createCustomer("Tom","Su"));
		Mockito.when(
				customerService.isValidCustomer(Mockito.any(Customer.class)))
				.thenReturn(true);
		// Send course as body to /customer
		String customerJson = "{\"firstName\":\"mkyong\",\"lastName\":\"abc\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CUSTOMER_API_URI).accept(MediaType.APPLICATION_JSON)
				.content(customerJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}
	// ---------------------------------------------------------------------------------------------------

	@Test
	public void deleteInvalidCustomer() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				CUSTOMER_API_URI + "/{id}", 1);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}
	// ---------------------------------------------------------------------------------------------------

	@Test
	public void deleteValidCustomer() throws Exception {
		Mockito.when(customerService.getCustomerById(1)).thenReturn(
				CustomerUtils.createCustomer("Tom","Su"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				CUSTOMER_API_URI + "/{id}", 1);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

	}
	// ---------------------------------------------------------------------------------------------------

	@Test
	public void listCustomers() throws Exception {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(CustomerUtils.createCustomer("Tom", "Cew"));
		customerList.add(CustomerUtils.createCustomer("Jimmy", "Tu"));
		Mockito.when(customerService.listCustomer()).thenReturn(
				customerList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(CUSTOMER_API_URI);

		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].firstName", is("Tom")))
			.andExpect(jsonPath("$[0].lastName", is("Cew")))
			.andExpect(jsonPath("$[1].firstName", is("Jimmy")))
			.andExpect(jsonPath("$[1].lastName", is("Tu")));

	}

	
}
