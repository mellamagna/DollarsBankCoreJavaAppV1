package com.cognixia.jump.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Transaction;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestTransactionDAOImpl {
	
	private TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl();
	
	private static final String TEST_USER_ID = "testUser";
	private static final Date TEST_DATE = new Date(2021, 3, 22);
	private static final int TEST_TYPE = 1;
	private static final int TEST_SOURCE = 1;
	private static final long TEST_AMOUNT = 1000;
	
	private static List<Transaction> current = null;
	private static int currentId = 0;

	@Test
	@Order(1)
	void testGetAllTransactions() {
		List<Transaction> transactions = transactionDAOImpl.getAllTransactions();
		assertNotNull(transactions);
	}
	
	@Test
	@Order(2)
	void testAddTransaction() {
		Transaction transaction = new Transaction(0, TEST_USER_ID, TEST_DATE, TEST_TYPE, TEST_SOURCE, TEST_AMOUNT);
		assertEquals(true, transactionDAOImpl.addTransaction(transaction));
	}
	
	@Test
	@Order(3)
	void testGetTransactionsByUserId() {
		current = transactionDAOImpl.getTransactionsByUserId(TEST_USER_ID);
		assertNotNull(current);
	}
	
	@Test
	@Order(4)
	void testUpdateTransaction() {
		try {
			Transaction transaction = current.get(0);
			currentId = transaction.getTransactionId();
			
			Date newDate = new Date(2020, 3, 22);
			int newType = 3;
			int newSource = 2;
			long newAmount = 10000;
			
			assertEquals(transaction.getDate().compareTo(TEST_DATE), 0);
			assertEquals(transaction.getType(), TEST_TYPE);
			assertEquals(transaction.getSource(), TEST_SOURCE);
			assertEquals(transaction.getAmount(), TEST_AMOUNT);
			
			transaction.setDate(newDate);
			transaction.setType(newType);
			transaction.setSource(newSource);
			transaction.setAmount(newAmount);
			
			if (transactionDAOImpl.updateTransaction(transaction)) {
				transaction = transactionDAOImpl.getTransactionByTransactionId(currentId);
				assertEquals(transaction.getDate().compareTo(newDate), 0);
				assertEquals(transaction.getType(), newType);
				assertEquals(transaction.getSource(), newSource);
				assertEquals(transaction.getAmount(), newAmount);
			}
			
		} catch (IndexOutOfBoundsException e) {
			fail("Empty list");
		} catch (NotFoundException e) {
			fail("Transaction to be updated not found in database");
		}
	}
	
	@Test
	@Order(5)
	void testDeleteTransactionByTransactionId() {
		assertEquals(true, transactionDAOImpl.deleteTransactionByTransactionId(currentId));
	}

}
