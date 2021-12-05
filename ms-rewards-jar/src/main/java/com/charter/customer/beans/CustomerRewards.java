package com.charter.customer.beans;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This bean class holds Response of Customer rewards summary API
 * @author saiku
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"customerId", "name", "totalRewardCount", "monthlyRewards"})
public class CustomerRewards implements Serializable {

	private static final long serialVersionUID = -6507961832501182405L;
	
	@JsonProperty("customerId")
	private int customerId;
	
	@JsonProperty("name")
	private String customerName;
	
	@JsonProperty("totalRewardCount")
	private int totalRewards;
	
	@JsonProperty("monthlyRewards")
	private List<MonthlyRewards> monthlyRewards;

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the totalRewards
	 */
	public int getTotalRewards() {
		return totalRewards;
	}

	/**
	 * @param totalRewards the totalRewards to set
	 */
	public void setTotalRewards(int totalRewards) {
		this.totalRewards = totalRewards;
	}

	/**
	 * @return the monthlyRewards
	 */
	public List<MonthlyRewards> getMonthlyRewards() {
		return monthlyRewards;
	}

	/**
	 * @param monthlyRewards the monthlyRewards to set
	 */
	public void setMonthlyRewards(List<MonthlyRewards> monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}

}
