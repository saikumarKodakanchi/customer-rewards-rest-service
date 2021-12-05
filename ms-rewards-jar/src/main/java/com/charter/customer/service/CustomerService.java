package com.charter.customer.service;

import com.charter.customer.beans.Customer;

/**
 * This Service class provides various methods related to Customer
 * @author saiku
 *
 */
public interface CustomerService {
	
	/**
	 * This method returns Customer info based on provided id
	 * @param id
	 * @return Customer details
	 */
	Customer getCustomer(int id);

}
