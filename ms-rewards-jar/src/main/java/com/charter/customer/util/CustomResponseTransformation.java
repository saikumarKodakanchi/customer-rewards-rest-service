package com.charter.customer.util;

import org.apache.commons.lang3.StringUtils;

import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.beans.CustomerRewardsResponse;
import com.charter.customer.beans.ServiceMessage;

/**
 * This class transforms the response
 * @author saiku
 *
 */
public class CustomResponseTransformation {
	
	/**
	 * This method transforms successful reward summary response
	 * @param rewards
	 * @return
	 */
	public static CustomerRewardsResponse returnRewardsSummarySuccessResponse(CustomerRewards rewards) {
		CustomerRewardsResponse response = new CustomerRewardsResponse();
		ServiceMessage message = new ServiceMessage();
		message.setCode("200");
		message.setType("OK");
		message.setDescription("Reward Summary successfully retrieved");
		response.setMessage(message);
		response.setRewards(rewards);
		return response;
	} 
	
	/**
	 * This method transforms failed reward summary response
	 * @param errorMessage
	 * @return
	 */
	public static CustomerRewardsResponse returnRewardsSummaryErrorResponse(String errorMessage) {
		CustomerRewardsResponse response = new CustomerRewardsResponse();
		response.setMessage(parseErrorMessage(errorMessage));
		return response;
	}

	/**
	 * This method parses the error message and returns in @ServiceMessage format
	 * @param error
	 * @return
	 */
	private static ServiceMessage parseErrorMessage(String error) {
		final String UNKNOWN = "UNKNOWN";
		final String ERROR_CODE_500 = "500";
		final String ERROR_TYPE_500 = "Internal Server Error";
		String code;
		String type;
		String description;
		final int CODE_LEN=7;
		final int TYPE_LEN=7;
		final int DESCRIPTION_LEN=14;
		
		if (StringUtils.isNotBlank(error)) {
			try {
				code = error.substring(error.indexOf("CODE =") + CODE_LEN, error.indexOf("TYPE =")).trim();
			} catch (StringIndexOutOfBoundsException e) {
				code = UNKNOWN;
			}
			
			try {
				type = error.substring(error.indexOf("TYPE =") + TYPE_LEN , error.indexOf("DESCRIPTION =")).trim();
			} catch (StringIndexOutOfBoundsException e) {
				type = UNKNOWN;
			}
			
			if (UNKNOWN.equals(code) || UNKNOWN.equals(type)) {
				code = ERROR_CODE_500;
				type = ERROR_TYPE_500;
				description = error;
			} else {
				description = error.substring(error.indexOf("DESCRIPTION =") + DESCRIPTION_LEN).trim();
			}
			
		} else {
			code = UNKNOWN;
			type = UNKNOWN;
			description = UNKNOWN;
		}
		
		ServiceMessage message = new ServiceMessage();
		message.setCode(code);
		message.setType(type);
		message.setDescription(description);
		return message;
	}

}
