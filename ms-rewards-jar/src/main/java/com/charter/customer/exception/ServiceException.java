package com.charter.customer.exception;

/**
 * Custom Service exception
 * @author saiku
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -644592492195008360L;

	/**
	 * Instantiates ServiceException with custom exception message
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}
	/**
	 * Instantiates ServiceException with custom exception message and cause
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
