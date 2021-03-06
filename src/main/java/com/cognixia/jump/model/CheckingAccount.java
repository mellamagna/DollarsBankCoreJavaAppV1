package com.cognixia.jump.model;

public class CheckingAccount {
	
	private int accountId;
	private String userId;
	private long balance;
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
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
	public CheckingAccount(int accountId, String userId, long balance) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "CheckingAccount [accountId=" + accountId + ", userId=" + userId + ", balance=" + balance + "]";
	}
	
	

}
