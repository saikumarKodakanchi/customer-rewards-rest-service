package com.charter.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charter.customer.beans.Customer;
import com.charter.customer.dao.CustomerRepository;
import com.charter.customer.service.CustomerService;

/**
 * This is the implementation class of @CustomerService
 * @author saiku
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * This method returns Customer info based on provided id
	 * @param id
	 * @return Customer details
	 */
	@Override
	public Customer getCustomer(int id) {
		return customerRepository.findById(id).orElse(null);
	}

}
