package com.cognixia.jump.model;

public class SavingsAccount {
	
	private String userId;
	private long balance;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public SavingsAccount(String userId, long balance) {
		super();
		this.userId = userId;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "SavingsAccount [userId=" + userId + ", balance=" + balance + "]";
	}
	
	

}
