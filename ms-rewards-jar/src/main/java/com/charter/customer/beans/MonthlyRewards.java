package com.charter.customer.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This bean holds rewards by each month which is part of customer rewards summary API
 * @author saiku
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"month", "rewardsCount"})
public class MonthlyRewards implements Serializable {

	private static final long serialVersionUID = 3600599036550965769L;

	@JsonProperty("month")
	public String month;
	
	@JsonProperty("rewardsCount")
	public int rewards;

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the rewards
	 */
	public int getRewards() {
		return rewards;
	}

	/**
	 * @param rewards the rewards to set
	 */
	public void setRewards(int rewards) {
		this.rewards = rewards;
	}
}
