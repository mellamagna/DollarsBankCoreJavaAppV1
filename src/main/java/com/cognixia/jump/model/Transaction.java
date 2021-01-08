package com.cognixia.jump.model;

import java.sql.Date;

public class Transaction {
	
	private String userId;
	private Date date;
	private long amount;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public Transaction(String userId, Date date, long amount) {
		super();
		this.userId = userId;
		this.date = date;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Transaction [userId=" + userId + ", date=" + date + ", amount=" + amount + "]";
	}
	
	
	
}
