package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> getAllTransactions();
	
	public Transaction getTransactionByTransactionId(int id) throws NotFoundException;
	
	public boolean addTransaction(Transaction tr);
	
	public boolean deleteTransactionByTransactionId(int id);
	
	public boolean updateTransaction(Transaction tr);
	
	public List<Transaction> getTransactionsByUserId(String userId);

}
