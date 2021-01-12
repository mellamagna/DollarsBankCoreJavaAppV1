package com.cognixia.jump.dao;

import java.util.List;

import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.dao.exceptions.UsernameUnavailableException;
import com.cognixia.jump.model.Account;

public interface AccountDAO {
	
	public List<Account> getAllAccounts();
	
	public Account getAccountByUserId(String userId) throws NotFoundException;

	public boolean addAccount(Account account) throws UsernameUnavailableException;
	
	public boolean deleteAccountByUserId(String userId);
	
	public boolean updateAccount(Account account);
	
}
