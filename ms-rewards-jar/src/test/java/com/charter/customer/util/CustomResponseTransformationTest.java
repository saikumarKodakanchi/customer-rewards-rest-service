package com.charter.customer.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charter.customer.beans.CustomerRewards;
import com.charter.customer.beans.CustomerRewardsResponse;
import com.charter.customer.constants.RewardConstants;

@ExtendWith(SpringExtension.class)
public class CustomResponseTransformationTest {
	
	private static final String TEST_ERROR = "test error";

	//This method tests for successful Reward Summary response
	@Test
	public void testReturnRewardsSummarySuccessResponse() {
		CustomerRewards rewards = new CustomerRewards();
		rewards.setCustomerId(1);
		CustomerRewardsResponse response = CustomResponseTransformation.returnRewardsSummarySuccessResponse(rewards);
		assertNotNull(response);
		assertNotNull(response.getRewards());
		assertEquals(1, response.getRewards().getCustomerId());
		assertNotNull(response.getMessage());
		assertEquals("200", response.getMessage().getCode());
	}
	
	//This method tests for Reward Summary response - Bad request
	@Test
	public void testReturnRewardsSummaryErrorResponse_BadRequest() {
		String errorMessage = String.format(RewardConstants.STATUS_DESCRIPTION_BAD_REQUEST, TEST_ERROR);
		CustomerRewardsResponse response = CustomResponseTransformation.returnRewardsSummaryErrorResponse(errorMessage);
		assertNotNull(response);
		assertNull(response.getRewards());
		assertNotNull(response.getMessage());
		assertEquals("400", response.getMessage().getCode());
		assertEquals("Bad Request", response.getMessage().getType());
		assertEquals(TEST_ERROR, response.getMessage().getDescription());
	}
	
	//This method tests for Reward Summary response - Internal server error when error message is not in proper format
	@Test
	public void testReturnRewardsSummaryErrorResponse_invalidErrorFormat() {
		CustomerRewardsResponse response = CustomResponseTransformation.returnRewardsSummaryErrorResponse(TEST_ERROR);
		assertNotNull(response);
		assertNull(response.getRewards());
		assertNotNull(response.getMessage());
		assertEquals("500", response.getMessage().getCode());
		assertEquals("Internal Server Error", response.getMessage().getType());
		assertEquals(TEST_ERROR, response.getMessage().getDescription());
	}
	
	//This method tests for Reward Summary response - UNKNOWN error when error message is empty
	@Test
	public void testReturnRewardsSummaryErrorResponse_emptyError() {
		CustomerRewardsResponse response = CustomResponseTransformation.returnRewardsSummaryErrorResponse("");
		assertNotNull(response);
		assertNull(response.getRewards());
		assertNotNull(response.getMessage());
		assertEquals("UNKNOWN", response.getMessage().getCode());
		assertEquals("UNKNOWN", response.getMessage().getType());
		assertEquals("UNKNOWN", response.getMessage().getDescription());
	}
}
