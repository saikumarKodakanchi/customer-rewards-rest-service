package com.charter.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ServiceExceptionTest {
	
	private static final String TEST = "test";
	
	//This method tests for ServiceException
	@Test
	public void testServiceExceptionConstructor() {
		ServiceException se = new ServiceException(TEST);
		assertEquals(TEST, se.getMessage());
		
		Throwable t = new IllegalArgumentException();
		se = new ServiceException(TEST, t);
		assertEquals(TEST, se.getMessage());
		assertEquals(t, se.getCause());
	}

}
