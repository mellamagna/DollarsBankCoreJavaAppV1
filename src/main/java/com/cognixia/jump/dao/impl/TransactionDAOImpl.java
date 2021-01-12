package com.cognixia.jump.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.TransactionDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public List<Transaction> getAllTransactions() {
		
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		
		try (PreparedStatement pstmt = conn.prepareStatement("select * from transaction");
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				int transactionId = rs.getInt("transaction_id");
				String userId = rs.getString("user_id");
				Date date = rs.getDate("trans_date");
				int type = rs.getInt("type");
				int source = rs.getInt("source");
				long amount = rs.getLong("amount");
				
				Transaction transaction = new Transaction(transactionId, userId, date, type, source, amount);
				result.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Transaction getTransactionByTransactionId(int id) throws NotFoundException {
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from transaction where transaction_id = ?")
				) {
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String userId = rs.getString("user_id");
				Date date = rs.getDate("trans_date");
				int type = rs.getInt("type");
				int source = rs.getInt("source");
				long amount = rs.getLong("amount");
				
				Transaction transaction = new Transaction(id, userId, date, type, source, amount);
				return transaction;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NotFoundException(id, "transaction");
	}

	@Override
	public boolean addTransaction(Transaction transaction) {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into transaction values(null, ?,?,?,?,?)")) {
			
			pstmt.setString(1, transaction.getUserId());
			pstmt.setDate(2, transaction.getDate());
			pstmt.setInt(3, transaction.getType());
			pstmt.setInt(4, transaction.getSource());
			pstmt.setLong(5, transaction.getAmount());
			
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
	public boolean deleteTransactionByTransactionId(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from transaction where transaction_id = ?")) {
			
			pstmt.setInt(1, id);
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
	public boolean updateTransaction(Transaction transaction) {
		final String statement = 
				"update transaction "
				+ "set "
				+ "user_id = ? , "
				+ "date = ? , "
				+ "type = ? , "
				+ "source = ? , "
				+ "amount = ? "
				+ "WHERE transaction_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
			
			pstmt.setString(1, transaction.getUserId());
			pstmt.setDate(2, transaction.getDate());
			pstmt.setInt(3, transaction.getType());
			pstmt.setInt(4, transaction.getSource());
			pstmt.setLong(5, transaction.getAmount());
			pstmt.setInt(6, transaction.getTransactionId());
			
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
	public List<Transaction> getTransactionsByUserId(String userId) {
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from transaction where user_id = ?")) {
			
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int transactionId = rs.getInt("transaction_id");
				Date date = rs.getDate("trans_date");
				int type = rs.getInt("type");
				int source = rs.getInt("source");
				long amount = rs.getLong("amount");
				
				Transaction transaction = new Transaction(transactionId, userId, date, type, source, amount);
				result.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	

}
