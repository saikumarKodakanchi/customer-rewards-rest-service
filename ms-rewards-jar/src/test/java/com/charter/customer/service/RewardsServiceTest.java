package com.charter.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.customer.beans.Customer;
import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.dao.TransactionRepository;
import com.charter.customer.exception.ServiceException;
import com.charter.customer.service.impl.RewardsServiceImpl;
import com.charter.customer.util.TestUtil;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RewardsServiceImpl.class, CustomerService.class, TransactionRepository.class})
public class RewardsServiceTest {
	
	@Autowired
	private RewardsService rewardsService;
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	//This method tests for successful retrieval of reward summary
	@Test
	public void testGetCustomerRewardSummary() throws ServiceException {
		Customer customer = TestUtil.getCustomer();
		when(customerService.getCustomer(anyInt())).thenReturn(customer);
		when(transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(TestUtil.getTransactionList());
		
		CustomerRewards rewards = rewardsService.getCustomerRewardSummary(
				1, LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 1));
		
		verify(customerService, times(1)).getCustomer(anyInt());
		verify(transactionRepository, times(1)).findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class));
		
		assertNotNull(rewards);
		assertEquals(1, rewards.getCustomerId());
		assertEquals("John Doe", rewards.getCustomerName());
		assertEquals(255, rewards.getTotalRewards());
		assertEquals(2, rewards.getMonthlyRewards().size());
		assertEquals("Sep-2021", rewards.getMonthlyRewards().get(0).getMonth());
		assertEquals(150, rewards.getMonthlyRewards().get(0).getRewards());
		assertEquals("Oct-2021", rewards.getMonthlyRewards().get(1).getMonth());
		assertEquals(105, rewards.getMonthlyRewards().get(1).getRewards());
	}
	
	//This method tests for Bad request service exception when invalid customer id is passed
	@Test
	public void testGetCustomerRewardSummary_invalidCustomerId() throws ServiceException {
		when(customerService.getCustomer(anyInt())).thenReturn(TestUtil.getCustomer());
		when(transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(TestUtil.getTransactionList());
		
		
		ServiceException se = Assertions.assertThrows(ServiceException.class, () -> {
			rewardsService.getCustomerRewardSummary(
					-1, LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 1));
		});
		
		verify(customerService, times(0)).getCustomer(anyInt());
		verify(transactionRepository, times(0)).findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class));
		
		assertEquals("CODE = 400 TYPE = Bad Request DESCRIPTION = Customer id should be a non-negative number.", se.getMessage());
	}
	
	//This method tests for Bad request service exception when invalid search date range is passed
	@Test
	public void testGetCustomerRewardSummary_invalidSearchDateRange() throws ServiceException {
		when(customerService.getCustomer(anyInt())).thenReturn(TestUtil.getCustomer());
		when(transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(TestUtil.getTransactionList());
		
		
		ServiceException se = Assertions.assertThrows(ServiceException.class, () -> {
			rewardsService.getCustomerRewardSummary(
					1, LocalDate.of(2021, 12, 1), LocalDate.of(2021, 9, 1));
		});
		
		verify(customerService, times(1)).getCustomer(anyInt());
		verify(transactionRepository, times(0)).findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class));
		
		assertEquals("CODE = 400 TYPE = Bad Request DESCRIPTION = Search start date should be less than end date.", se.getMessage());
	}
	
	//This method tests for Bad request service exception when customer is not found with passed id
	@Test
	public void testGetCustomerRewardSummary_invalidCustomer() throws ServiceException {
		when(customerService.getCustomer(anyInt())).thenReturn(null);
		when(transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(TestUtil.getTransactionList());
		
		
		ServiceException se = Assertions.assertThrows(ServiceException.class, () -> {
			rewardsService.getCustomerRewardSummary(
					1, LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 1));
		});
		
		verify(customerService, times(1)).getCustomer(anyInt());
		verify(transactionRepository, times(0)).findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class));
		
		assertEquals("CODE = 400 TYPE = Bad Request DESCRIPTION = Customer not found.", se.getMessage());
	}

	//This method tests for Not found service exception when there are no transactions matching provided search date range
	@Test
	public void testGetCustomerRewardSummary_transactionsNotFound() throws ServiceException {
		when(customerService.getCustomer(anyInt())).thenReturn(TestUtil.getCustomer());
		when(transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class)))
		.thenReturn(null);
		
		
		ServiceException se = Assertions.assertThrows(ServiceException.class, () -> {
			rewardsService.getCustomerRewardSummary(
					1, LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 1));
		});
		
		verify(customerService, times(1)).getCustomer(anyInt());
		verify(transactionRepository, times(1)).findTransactionByCustomerIdAndCreatedTsBetween(
				anyInt(), any (LocalDateTime.class), any(LocalDateTime.class));
		
		assertEquals("CODE = 404 TYPE = Not Found DESCRIPTION = No transactions found for the customer with provided search date range.",
				se.getMessage());
	}

}
