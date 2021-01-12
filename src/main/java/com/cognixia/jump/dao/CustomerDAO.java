package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Customer;

public interface CustomerDAO {
	
	public List<Customer> getAllCustomers();
	
	public Customer getCustomerByUserId(String userId) throws NotFoundException;
	
	public boolean addCustomer(Customer customer);
	
	public boolean deleteCustomerByUserId(String userId);
	
	public boolean updateCustomer(Customer customer);

}
