package com.charter.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.charter.customer.RewardsApplication;
import com.charter.customer.configuration.SecurityConfig;
import com.charter.customer.constants.RewardConstants;
import com.charter.customer.exception.ServiceException;
import com.charter.customer.service.RewardsService;
import com.charter.customer.util.TestUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerRewardsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RewardsApplication.class, CustomerRewardsController.class, RewardsService.class, SecurityConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerRewardsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RewardsService rewardsService;
	
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "secret";
	private static final String REWARD_SUMMARY_URI = "/v1/customers/1/rewardsummary?startDate=2021-09-01&endDate=2021-12-01";
	private static final String MESSAGE_CODE_JSONPATH = "$.message.code";
	private static final String MESSAGE_DESC_JSONPATH = "$.message.description";
	private static final String TOTAL_REWARD_JSONPATH = "$.rewards.totalRewardCount";
	private static final String TEST_STRING = "Test";

	//This method tests for successful calculation of reward summary
	@Test
	public void testGetRewardSummary() throws Exception {
		when(rewardsService.getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class)))
		.thenReturn(TestUtil.getCustomerRewards());
		
		mockMvc.perform(MockMvcRequestBuilders.get(REWARD_SUMMARY_URI).with(httpBasic(USERNAME, PASSWORD)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath(MESSAGE_CODE_JSONPATH).value("200"))
		.andExpect(jsonPath(TOTAL_REWARD_JSONPATH).value(150));
		
		verify(rewardsService, times(1)).getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class));
	}
	
	//This method tests for Bad request when service throws Basd request exception
	@Test
	public void testGetRewardSummary_BadRequest() throws Exception {
		when(rewardsService.getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class)))
		.thenThrow(new ServiceException(String.format(RewardConstants.STATUS_DESCRIPTION_BAD_REQUEST, TEST_STRING)));
		
		mockMvc.perform(MockMvcRequestBuilders.get(REWARD_SUMMARY_URI).with(httpBasic(USERNAME, PASSWORD)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath(MESSAGE_CODE_JSONPATH).value("400"))
		.andExpect(jsonPath(MESSAGE_DESC_JSONPATH).value(TEST_STRING));
		
		verify(rewardsService, times(1)).getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class));
	}
	
	//This method tests for internal server exception when service throws unexpected runtime exception
	@Test
	public void testGetRewardSummary_InvalidException() throws Exception {
		when(rewardsService.getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class)))
		.thenThrow(new RuntimeException());
		
		mockMvc.perform(MockMvcRequestBuilders.get(REWARD_SUMMARY_URI).with(httpBasic(USERNAME, PASSWORD)))
		.andExpect(MockMvcResultMatchers.status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath(MESSAGE_CODE_JSONPATH).value("UNKNOWN"))
		.andExpect(jsonPath(MESSAGE_DESC_JSONPATH).value("UNKNOWN"));
		
		verify(rewardsService, times(1)).getCustomerRewardSummary(anyInt(), any(LocalDate.class), any(LocalDate.class));
	}
	
}
