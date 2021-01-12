package com.cognixia.jump.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.CheckingAccount;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCheckingAccountDAOImpl {
	
	private CheckingAccountDAOImpl checkingAccountDAOImpl = new CheckingAccountDAOImpl();
	
	private static final String TEST_USER_ID = "testUser";
	private static final long TEST_BALANCE = 1000000;

	@Test
	@Order(1)
	void testGetAllCheckingAccounts() {
		List<CheckingAccount> accounts = checkingAccountDAOImpl.getAllCheckingAccounts();
		assertNotNull(accounts);
	}
	
	@Test
	@Order(2)
	void testAddCheckingAccount() {
		CheckingAccount account = new CheckingAccount(0, TEST_USER_ID, TEST_BALANCE);
		assertEquals(true, checkingAccountDAOImpl.addCheckingAccount(account));
	}
	
	@Test
	@Order(3)
	void testGetCheckingAccountByUserId() {
		try {
			assertNotNull(checkingAccountDAOImpl.getCheckingAccountByUserId(TEST_USER_ID));
		} catch (NotFoundException e) {
			fail("CheckingAccount not found");
		}
		assertThrows(NotFoundException.class, () -> {checkingAccountDAOImpl.getCheckingAccountByUserId("not a real account"); });
	}
	
	@Test
	@Order(4)
	void testUpdateCheckingAccount() {
		try {
			long newBalance = 1000;
			CheckingAccount account = checkingAccountDAOImpl.getCheckingAccountByUserId(TEST_USER_ID);
			assertEquals(account.getBalance(), TEST_BALANCE);
			account.setBalance(newBalance);
			if (checkingAccountDAOImpl.updateCheckingAccount(account)) {
				account = checkingAccountDAOImpl.getCheckingAccountByUserId(TEST_USER_ID);
				assertEquals(account.getBalance(), newBalance);
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	void testDeleteCheckingAccountByUserId() {
		assertEquals(true, checkingAccountDAOImpl.deleteCheckingAccountByUserId(TEST_USER_ID));
	}

}
