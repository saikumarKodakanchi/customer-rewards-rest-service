package com.charter.customer.service;

import java.time.LocalDate;

import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.exception.ServiceException;

/**
 * This service class holds all methods related to Rewards
 * @author saiku
 *
 */
public interface RewardsService {
	
	/**
	 * This method provides Summary of customer rewards based on provided search date range
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ServiceException
	 */
	CustomerRewards getCustomerRewardSummary(int customerId, LocalDate startDate, LocalDate endDate) throws ServiceException;

}
