package com.cognixia.jump.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.SavingsAccountDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.SavingsAccount;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public List<SavingsAccount> getAllSavingsAccounts() {
		ArrayList<SavingsAccount> result = new ArrayList<SavingsAccount>();
		
		try (PreparedStatement pstmt = conn.prepareStatement("select * from savings_account");
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				int accountId = rs.getInt("account_id");
				String userId = rs.getString("user_id");
				long balance = rs.getLong("balance");
				
				SavingsAccount sa = new SavingsAccount(accountId, userId, balance);
				result.add(sa);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public SavingsAccount getSavingsAccountByUserId(String userId) throws NotFoundException {
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from savings_account where user_id = ?")) {
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int accountId = rs.getInt("account_id");
				long balance = rs.getLong("balance");
				
				SavingsAccount sa = new SavingsAccount(accountId, userId, balance);
				return sa;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NotFoundException(userId, "savings_account");
		
	}

	@Override
	public boolean addSavingsAccount(SavingsAccount sa) {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into savings_account values(null, ?,?)")) {
			
			pstmt.setString(1, sa.getUserId());
			pstmt.setLong(2, sa.getBalance());
			
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
	public boolean deleteSavingsAccountByUserId(String userId) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from savings_account where user_id = ?")) {
			
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
	public boolean updateSavingsAccount(SavingsAccount sa) {
		try (PreparedStatement pstmt = conn.prepareStatement("update savings_account set balance = ? where user_id = ?")) {
			
			pstmt.setLong(1, sa.getBalance());
			pstmt.setString(2, sa.getUserId());
			
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
