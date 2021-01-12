package com.cognixia.jump.dao.exceptions;

public class UsernameUnavailableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2316293019073597107L;

	public UsernameUnavailableException (String username, String table) {
		super("The username \"" + username + "\" already exists in the target table \"" + table + "\".");
	}

}
