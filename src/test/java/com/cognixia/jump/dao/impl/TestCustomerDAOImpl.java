package com.cognixia.jump.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Customer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCustomerDAOImpl {
	
	private CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
	
	private static final String TEST_USER_ID = "testUser";
	private static final String TEST_NAME = "John Smith";
	private static final String TEST_ADDRESS = "123 Test St";
	private static final String TEST_NUMBER = "1234567890";

	@Test
	@Order(1)
	void testGetAllCustomers() {
		List<Customer> customers = customerDAOImpl.getAllCustomers();
		assertNotNull(customers);
	}
	
	@Test
	@Order(2)
	void testAddCustomer() {
		Customer customer = new Customer(0, TEST_USER_ID, TEST_NAME, TEST_ADDRESS, TEST_NUMBER);
		assertEquals(true, customerDAOImpl.addCustomer(customer));
	}
	
	@Test
	@Order(3)
	void testGetCustomerByUserId() {
		try {
			assertNotNull(customerDAOImpl.getCustomerByUserId(TEST_USER_ID));
		} catch (NotFoundException e) {
			fail("Customer not found");
		}
		assertThrows(NotFoundException.class, () -> {customerDAOImpl.getCustomerByUserId("not a real customer"); });
	}
	
	@Test
	@Order(4)
	void testUpdateCustomer() {
		try {
			String newName = "Jane Doe";
			String newAddress = "124 Test Dr";
			String newNumber = "9876543210";
			Customer customer = customerDAOImpl.getCustomerByUserId(TEST_USER_ID);
			assertEquals(customer.getName(), TEST_NAME);
			assertEquals(customer.getAddress(), TEST_ADDRESS);
			assertEquals(customer.getNumber(), TEST_NUMBER);
			customer.setName(newName);
			customer.setAddress(newAddress);
			customer.setNumber(newNumber);
			if (customerDAOImpl.updateCustomer(customer)) {
				customer = customerDAOImpl.getCustomerByUserId(TEST_USER_ID);
				assertEquals(customer.getName(), newName);
				assertEquals(customer.getAddress(), newAddress);
				assertEquals(customer.getNumber(), newNumber);
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	void testDeleteCustomerByUserId() {
		assertEquals(true, customerDAOImpl.deleteCustomerByUserId(TEST_USER_ID));
	}

}
