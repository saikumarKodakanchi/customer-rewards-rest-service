package com.charter.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charter.customer.beans.Customer;

/**
 * This repository class handles all Customer table queries
 * @author saiku
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}
