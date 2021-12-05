package com.charter.customer.controller;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.beans.CustomerRewardsResponse;
import com.charter.customer.service.RewardsService;
import com.charter.customer.util.CustomResponseTransformation;

/**
 * This class handles all requests related to customer rewards
 * @author saiku
 *
 */
/*
 * There is a possibility to implement several other endpoints like below. But stricting to the said problem implemented only reward summary endpoint
 * 1. Saving customer data
 * 2. Retrieving all customers
 * 3. Retrieving customer by id
 * 4. Updating customer info
 * 5. Saving transaction
 * 6. Retrieving specific transaction
 * 7. Retrieving all transactions with various search criteria like date range, duration, paginated etc.
 */
@RestController
@RequestMapping("/v1")
public class CustomerRewardsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRewardsController.class);
	
	@Autowired
	private RewardsService rewardsService;


	/**
	 * This API calculates rewards earned by customer within provided search date range 
	 * and returns reward summary by month 
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping(value = "/customers/{customerId}/rewardsummary", produces= { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CustomerRewardsResponse> getRewardSummary(
			@PathVariable Integer customerId,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate) {
		CustomerRewardsResponse response = null;
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			CustomerRewards rewards = rewardsService.getCustomerRewardSummary(customerId, startDate, endDate);
			response = CustomResponseTransformation.returnRewardsSummarySuccessResponse(rewards);
		} catch (Exception ex) {
			LOGGER.error("Exception occured while calculating rewards summary: {}", ex.getMessage(), ex);
			response = CustomResponseTransformation.returnRewardsSummaryErrorResponse(ex.getMessage());
			String messageCode = response.getMessage().getCode();
			if (StringUtils.isNumeric(messageCode)) {
				httpStatus = HttpStatus.valueOf(Integer.parseInt(messageCode));
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		
		return new ResponseEntity<>(response, httpStatus);
	}
}
