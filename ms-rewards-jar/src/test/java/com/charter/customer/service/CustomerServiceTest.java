package com.charter.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.customer.beans.Customer;
import com.charter.customer.dao.CustomerRepository;
import com.charter.customer.service.impl.CustomerServiceImpl;
import com.charter.customer.util.TestUtil;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class, CustomerRepository.class})
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	//This method tests for successful retrieval of customer information
	@Test
	public void testGetCustomer() {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(TestUtil.getCustomer()));
		Customer customer = customerService.getCustomer(1);
		assertNotNull(customer);
		assertEquals(1, customer.getId());
		assertEquals("John Doe", customer.getName());
		verify(customerRepository, times(1)).findById(anyInt());
	}
	
	//This method tests for null when customer information is not found in database
	@Test
	public void testGetCustomer_NotFound() {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
		Customer customer = customerService.getCustomer(1);
		assertNull(customer);
		verify(customerRepository, times(1)).findById(anyInt());
	}
}
