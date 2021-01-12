package com.cognixia.jump.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.AccountDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.dao.exceptions.UsernameUnavailableException;
import com.cognixia.jump.model.Account;

public class AccountDAOImpl implements AccountDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	public List<Account> getAllAccounts() {

		ArrayList<Account> result = new ArrayList<Account>();
		
		try (PreparedStatement pstmt = conn.prepareStatement("select * from account");
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				String userId = rs.getString("user_id");
				String password = rs.getString("password");
				
				Account account = new Account(userId, password);
				result.add(account);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

	public Account getAccountByUserId(String userId) throws NotFoundException {
		
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from account where user_id = ?")
				) {
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String password = rs.getString("password");
				
				Account account = new Account(userId, password);
				return account;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NotFoundException(userId, "account");
		
	}

	public boolean addAccount(Account account) throws UsernameUnavailableException {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into account values(?,?)")) {
			
			pstmt.setString(1, account.getUserId());
			pstmt.setString(2, account.getPassword());
			
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteAccountByUserId(String userId) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from account where user_id = ?")) {
			
			pstmt.setString(1, userId);
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateAccount(Account account) {
		try (PreparedStatement pstmt = conn.prepareStatement("update account set password = ? where user_id = ?")) {
			
			pstmt.setString(1, account.getUserId());
			
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	
}
