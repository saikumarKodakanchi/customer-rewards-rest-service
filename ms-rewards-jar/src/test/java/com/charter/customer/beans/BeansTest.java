package com.charter.customer.beans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import com.charter.customer.exception.ServiceException;

public class BeansTest {
	
	private static class BeanTest {
		private String name;
		private Object bean;
		
		public BeanTest(String name, Object bean) {
			super();
			this.name = name;
			this.bean = bean;
		}
	}

	//This is common method which tests properties of bean
	public void testProperties(BeanTest beanTest) throws ServiceException {
		try {
			Map<String, Object> describe = PropertyUtils.describe(beanTest.bean);

			for (Entry<String, Object> entry : describe.entrySet()) {
				String key = entry.getKey();
				if (!"class".equals(key)) {
					PropertyUtils.setSimpleProperty(beanTest.bean, key, entry.getValue());
					Object actualValue = PropertyUtils.getSimpleProperty(beanTest.bean, key);
					assertEquals(entry.getValue(), actualValue, "Testing "+beanTest.name);
				}
			}
			
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	//This method tests properties of all beans
	@Test
	public void testBeans() throws ServiceException {
		BeanTest test = new BeanTest("Customer", new Customer());
		testProperties(test);
		
		test = new BeanTest("CustomerRewards", new CustomerRewards());
		testProperties(test);
		
		test = new BeanTest("CustomerRewardsResponse", new CustomerRewardsResponse());
		testProperties(test);
		
		test = new BeanTest("MonthlyRewards", new MonthlyRewards());
		testProperties(test);
		
		test = new BeanTest("ServiceMessage", new ServiceMessage());
		testProperties(test);
		
		test = new BeanTest("Transaction", new Transaction());
		testProperties(test);
		
	}

}
