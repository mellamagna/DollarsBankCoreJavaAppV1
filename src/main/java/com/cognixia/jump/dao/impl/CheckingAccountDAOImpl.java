package com.cognixia.jump.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.CheckingAccountDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.CheckingAccount;

public class CheckingAccountDAOImpl implements CheckingAccountDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public List<CheckingAccount> getAllCheckingAccounts() {
		ArrayList<CheckingAccount> result = new ArrayList<CheckingAccount>();
		
		try (PreparedStatement pstmt = conn.prepareStatement("select * from checking_account");
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				int accountId = rs.getInt("account_id");
				String userId = rs.getString("user_id");
				long balance = rs.getLong("balance");
				
				CheckingAccount ca = new CheckingAccount(accountId, userId, balance);
				result.add(ca);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public CheckingAccount getCheckingAccountByUserId(String userId) throws NotFoundException {
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from checking_account where user_id = ?")) {
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int accountId = rs.getInt("account_id");
				long balance = rs.getLong("balance");
				
				CheckingAccount ca = new CheckingAccount(accountId, userId, balance);
				return ca;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NotFoundException(userId, "checking_account");
		
	}

	@Override
	public boolean addCheckingAccount(CheckingAccount ca) {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into checking_account values(null, ?,?)")) {
			
			pstmt.setString(1, ca.getUserId());
			pstmt.setLong(2, ca.getBalance());
			
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

	@Override
	public boolean deleteCheckingAccountByUserId(String userId) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from checking_account where user_id = ?")) {
			
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

	@Override
	public boolean updateCheckingAccount(CheckingAccount ca) {
		try (PreparedStatement pstmt = conn.prepareStatement("update checking_account set balance = ? where user_id = ?")) {
			
			pstmt.setLong(1, ca.getBalance());
			pstmt.setString(2, ca.getUserId());
			
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
