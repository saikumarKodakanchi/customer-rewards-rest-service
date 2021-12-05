package com.charter.customer.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This bean holds response of Customer Rewards summary API
 * @author saiku
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "rewards"})
public class CustomerRewardsResponse implements Serializable {

	private static final long serialVersionUID = -7452406881318296902L;
	
	@JsonProperty("message")
	private ServiceMessage message;
	
	@JsonProperty("rewards")
	private CustomerRewards rewards;

	/**
	 * @return the message
	 */
	public ServiceMessage getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(ServiceMessage message) {
		this.message = message;
	}

	/**
	 * @return the rewards
	 */
	public CustomerRewards getRewards() {
		return rewards;
	}

	/**
	 * @param rewards the rewards to set
	 */
	public void setRewards(CustomerRewards rewards) {
		this.rewards = rewards;
	}
	
	
}
