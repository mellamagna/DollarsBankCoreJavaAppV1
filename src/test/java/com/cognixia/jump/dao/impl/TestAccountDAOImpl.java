package com.cognixia.jump.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.dao.exceptions.UsernameUnavailableException;
import com.cognixia.jump.model.Account;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestAccountDAOImpl {
	
	private AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
	
	private static final String TEST_USER_ID = "testUser";
	private static final String TEST_PASSWORD = "123456";

	@Test
	@Order(1)
	void testGetAllAccounts() {
		List<Account> accounts = accountDAOImpl.getAllAccounts();
		assertNotNull(accounts);
	}
	
	@Test
	@Order(2)
	void testAddAccount() {
		Account account = new Account(TEST_USER_ID, TEST_PASSWORD);
		try {
			assertEquals(true, accountDAOImpl.addAccount(account));
		} catch (UsernameUnavailableException e) {
			fail("Username taken");
		}
	}
	
	@Test
	@Order(3)
	void testGetAccountByUserId() {
		try {
			assertNotNull(accountDAOImpl.getAccountByUserId(TEST_USER_ID));
		} catch (NotFoundException e) {
			fail("Account not found");
		}
		assertThrows(NotFoundException.class, () -> {accountDAOImpl.getAccountByUserId("not a real account"); });
	}
	
	@Test
	@Order(4)
	void testUpdateAccount() {
		try {
			String newPassword = "789012";
			Account account = accountDAOImpl.getAccountByUserId(TEST_USER_ID);
			assertEquals(account.getPassword(), TEST_PASSWORD);
			account.setPassword(newPassword);
			if (accountDAOImpl.updateAccount(account)) {
				account = accountDAOImpl.getAccountByUserId(TEST_USER_ID);
				assertEquals(account.getPassword(), newPassword);
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	void testDeleteAccountByUserId() {
		assertEquals(true, accountDAOImpl.deleteAccountByUserId(TEST_USER_ID));
	}

}
