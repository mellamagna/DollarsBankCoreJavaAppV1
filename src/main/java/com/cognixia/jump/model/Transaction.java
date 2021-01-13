package com.cognixia.jump.model;

import java.sql.Date;

public class Transaction {
	
	private int transactionId;
	private String userId;
	private Date date;
	private int type;
	private int source;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Transaction(int transactionId, String userId, Date date, int type, int source, long amount) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.date = date;
		this.type = type;
		this.source = source;
		this.amount = amount;
	}
	@Override
	public String toString() {
		String transType;
		String transSource;
		String conjunction = " from ";
		switch (this.type) {
		case 1:
			transType = "deposit";
			conjunction = " to ";
			break;
		case 2:
			transType = "withdrawal";
			break;
		case 3:
			transType = "transfer";
			break;
		default:
			transType = "deposit";
			break;
		}
		switch (this.source) {
		case 1:
			transSource = "checking";
			break;
		case 2:
			transSource = "savings";
			break;
		default:
			transSource = "checking";
			break;
		}
		return "[" + this.date + " " + "ID:" + this.transactionId + "] " + transType
				+ conjunction + transSource + " ($" + this.amount + ")";
	}
	
	
	
	
	
	
}
