package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.SavingsAccount;

public interface SavingsAccountDAO {
	
	public List<SavingsAccount> getAllSavingsAccounts();
	
	public SavingsAccount getSavingsAccountByUserId(String userId) throws NotFoundException;
	
	public boolean addSavingsAccount(SavingsAccount sa);
	
	public boolean deleteSavingsAccountByUserId(String userId);
	
	public boolean updateSavingsAccount(SavingsAccount sa);

}
