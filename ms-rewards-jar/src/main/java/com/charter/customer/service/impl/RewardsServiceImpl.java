package com.charter.customer.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.charter.customer.beans.Customer;
import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.beans.MonthlyRewards;
import com.charter.customer.beans.Transaction;
import com.charter.customer.constants.RewardConstants;
import com.charter.customer.dao.TransactionRepository;
import com.charter.customer.exception.ServiceException;
import com.charter.customer.service.CustomerService;
import com.charter.customer.service.RewardsService;

/**
 * This is the implementation class of @RewardsService
 * @author saiku
 *
 */
@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerService customerService;

	@Override
	public CustomerRewards getCustomerRewardSummary(int customerId, LocalDate startDate, LocalDate endDate) throws ServiceException {

		validateInputData(customerId, startDate, endDate);

		CustomerRewards rewards = new CustomerRewards();

		LocalDateTime startDateTime = startDate.atTime(0, 0, 0);
		LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

		List<Transaction> transactions =  transactionRepository.findTransactionByCustomerIdAndCreatedTsBetween(customerId, startDateTime, endDateTime);

		if (CollectionUtils.isEmpty(transactions)) {
			throw new ServiceException(String.format(RewardConstants.STATUS_DESCRIPTION_NOT_FOUND, "No transactions found for the customer with provided search date range."));
		} else {

			int totalRewards = 0;
			Map<String, Integer> custMonthlyTxMap = new LinkedHashMap<>();
			for (Transaction transaction : transactions) {

				rewards.setCustomerId(transaction.getCustomer().getId());
				rewards.setCustomerName(transaction.getCustomer().getName());

				String month = getMonth(transaction.getCreatedTs());
				int monthlyRewards = 0;

				//Gets existing reward points of the current transaction month
				if (custMonthlyTxMap.containsKey(month)) {
					monthlyRewards = custMonthlyTxMap.get(month);
				}

				int currentTransactionRewards = getEarnedRewardPoints(transaction.getAmount().floatValue());
				monthlyRewards += currentTransactionRewards;
				totalRewards += currentTransactionRewards;

				custMonthlyTxMap.put(month, monthlyRewards);
			}
			rewards.setTotalRewards(totalRewards);
			rewards.setMonthlyRewards(getMonthlyRewards(custMonthlyTxMap));
		}
		return rewards;
	}

	/**
	 * This method formats and returns earned rewards by month
	 * @param custMonthlyTxMap
	 * @return
	 */
	private List<MonthlyRewards> getMonthlyRewards(Map<String, Integer> custMonthlyTxMap) {
		List<MonthlyRewards> monthlyRewardsList = new ArrayList<>();
		for (Entry<String, Integer> entry : custMonthlyTxMap.entrySet()) {
			MonthlyRewards monthlyRewards = new MonthlyRewards();
			monthlyRewards.setMonth(entry.getKey());
			monthlyRewards.setRewards(entry.getValue());
			monthlyRewardsList.add(monthlyRewards);
		}
		
		return monthlyRewardsList;
	}

	/**
	 * This method validates input methods and throws @ServiceException in case of any validation errors.
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @throws ServiceException
	 */
	private void validateInputData(Integer customerId, LocalDate startDate, LocalDate endDate) throws ServiceException {

		StringBuilder sb = new StringBuilder();

		if (customerId <= 0) {
			sb.append("Customer id should be a non-negative number.");
		}
		else {
			Customer customer = customerService.getCustomer(customerId);
			if (customer == null) {
				sb.append("Customer not found.");
			}
		}

		if (startDate.isAfter(endDate)) {
			sb.append("Search start date should be less than end date.");
		}

		if (sb.length() > 0) {
			throw new ServiceException(String.format(RewardConstants.STATUS_DESCRIPTION_BAD_REQUEST, sb.toString()));
		}

	}

	/**
	 * This method calculates reward points based on input purchase amount and returns it
	 * Logic:
	 * A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
	 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
	 * @param amount
	 * @return
	 */
	private static int getEarnedRewardPoints(float amount) {
		int rewardPoints = 0;
		if (amount > 100) {
			rewardPoints = 50 + (((int)amount-100) * 2);
		}
		else if (amount > 50) {
			rewardPoints = (int)amount-50;
		}

		return rewardPoints;
	}

	private static String getMonth(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("MMM-yyyy"));
	}

}
