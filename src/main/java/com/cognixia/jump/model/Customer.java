package com.cognixia.jump.model;

public class Customer {
	
	private int customerId;
	private String userId;
	private String name;
	private String address;
	private String number;
	
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public Customer(int customerId, String userId, String name, String address, String number) {
		super();
		this.customerId = customerId;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.number = number;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", userId=" + userId + ", name=" + name + ", address=" + address
				+ ", number=" + number + "]";
	}

	
	
	
	

}
