package com.charter.customer.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.charter.customer.beans.Customer;
import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.beans.MonthlyRewards;
import com.charter.customer.beans.Transaction;

/**
 * This util class is used by Junits for populating sample data
 * @author saiku
 *
 */
public class TestUtil {
	
	//This method returns sample Customer data
	public static Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("John Doe");
		return customer;
	}
	
	//This method returns sample Transaction data
	public static List<Transaction> getTransactionList() {
		List<Transaction> transactions = new ArrayList<>();
		
		transactions.add(getTransaction(150, 9));
		transactions.add(getTransaction(120, 10));
		transactions.add(getTransaction(65, 10));
		
		return transactions;
	}

	//This method returns sample Transaction data
	private static Transaction getTransaction(int amount, int month) {
		Transaction transaction = new Transaction();
		transaction.setCustomer(getCustomer());
		transaction.setCreatedTs(LocalDateTime.of(2021, month, 23, 11, 30));
		transaction.setAmount(new BigDecimal(amount));
		return transaction;
	}
	
	//This method returns sample CustomerRewards data
	public static CustomerRewards getCustomerRewards() {
		CustomerRewards rewards = new CustomerRewards();
		rewards.setCustomerId(1);
		rewards.setCustomerName("John Doe");
		rewards.setTotalRewards(150);
		List<MonthlyRewards> monthlyRewardsList = new ArrayList<>();
		monthlyRewardsList.add(getMonthlyRewards("Sep-2021", 100));
		monthlyRewardsList.add(getMonthlyRewards("Oct-2021", 50));
		rewards.setMonthlyRewards(monthlyRewardsList);
		return rewards;
	}

	//This method returns sample MonthlyRewards data
	private static MonthlyRewards getMonthlyRewards(String month, int rewards) {
		MonthlyRewards monthlyRewards = new MonthlyRewards();
		monthlyRewards.setMonth(month);
		monthlyRewards.setRewards(rewards);
		return monthlyRewards;
	}

}
