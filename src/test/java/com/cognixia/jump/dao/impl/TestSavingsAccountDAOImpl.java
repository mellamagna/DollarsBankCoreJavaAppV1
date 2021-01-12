package com.cognixia.jump.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.SavingsAccount;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestSavingsAccountDAOImpl {
	
	private SavingsAccountDAOImpl savingsAccountDAOImpl = new SavingsAccountDAOImpl();
	
	private static final String TEST_USER_ID = "testUser";
	private static final long TEST_BALANCE = 1000000;

	@Test
	@Order(1)
	void testGetAllSavingsAccounts() {
		List<SavingsAccount> accounts = savingsAccountDAOImpl.getAllSavingsAccounts();
		assertNotNull(accounts);
	}
	
	@Test
	@Order(2)
	void testAddSavingsAccount() {
		SavingsAccount account = new SavingsAccount(0, TEST_USER_ID, TEST_BALANCE);
		assertEquals(true, savingsAccountDAOImpl.addSavingsAccount(account));
	}
	
	@Test
	@Order(3)
	void testGetSavingsAccountByUserId() {
		try {
			assertNotNull(savingsAccountDAOImpl.getSavingsAccountByUserId(TEST_USER_ID));
		} catch (NotFoundException e) {
			fail("SavingsAccount not found");
		}
		assertThrows(NotFoundException.class, () -> {savingsAccountDAOImpl.getSavingsAccountByUserId("not a real account"); });
	}
	
	@Test
	@Order(4)
	void testUpdateSavingsAccount() {
		try {
			long newBalance = 1000;
			SavingsAccount account = savingsAccountDAOImpl.getSavingsAccountByUserId(TEST_USER_ID);
			assertEquals(account.getBalance(), TEST_BALANCE);
			account.setBalance(newBalance);
			if (savingsAccountDAOImpl.updateSavingsAccount(account)) {
				account = savingsAccountDAOImpl.getSavingsAccountByUserId(TEST_USER_ID);
				assertEquals(account.getBalance(), newBalance);
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	void testDeleteSavingsAccountByUserId() {
		assertEquals(true, savingsAccountDAOImpl.deleteSavingsAccountByUserId(TEST_USER_ID));
	}

}
