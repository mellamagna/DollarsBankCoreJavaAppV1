package com.cognixia.jump.dao.exceptions;

public class NotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857436024247522157L;
	
	public NotFoundException(String id, String table) {
		super("A query for the ID \"" + id + "\" in the target table \"" + table + "\" failed to return any results.");
	}
	
	public NotFoundException(int id, String table) {
		super("A query for the ID \"" + id + "\" in the target table \"" + table + "\" failed to return any results.");
	}

}
