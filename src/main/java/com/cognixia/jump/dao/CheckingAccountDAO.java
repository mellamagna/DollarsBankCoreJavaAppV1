package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.CheckingAccount;

public interface CheckingAccountDAO {
	
	public List<CheckingAccount> getAllCheckingAccounts();
	
	public CheckingAccount getCheckingAccountByUserId(String userId) throws NotFoundException;
	
	public boolean addCheckingAccount(CheckingAccount ca);
	
	public boolean deleteCheckingAccountByUserId(String userId);
	
	public boolean updateCheckingAccount(CheckingAccount ca);

}
