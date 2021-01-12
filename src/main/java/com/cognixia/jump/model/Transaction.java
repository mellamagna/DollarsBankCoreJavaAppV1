package com.cognixia.jump.model;

import java.sql.Date;

public class Transaction {
	
	private int transactionId;
	private String userId;
	private Date date;
	private long amount;
	
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
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
	public Transaction(int transactionId, String userId, Date date, long amount) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.date = date;
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", userId=" + userId + ", date=" + date + ", amount="
				+ amount + "]";
	}
	
	
	
	
}
