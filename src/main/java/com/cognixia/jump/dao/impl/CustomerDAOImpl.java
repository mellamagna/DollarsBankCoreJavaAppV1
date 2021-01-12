package com.cognixia.jump.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.CustomerDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {
	
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public List<Customer> getAllCustomers() {
		
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		try (PreparedStatement pstmt = conn.prepareStatement("select * from customer");
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				int customerId = rs.getInt("customer_id");
				String userId = rs.getString("user_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String number = rs.getString("number");
				
				Customer customer = new Customer(customerId, userId, name, address, number);
				result.add(customer);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Customer getCustomerByUserId(String userId) throws NotFoundException {
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement("select * from customer where user_id = ?")
				) {
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int customerId = rs.getInt("customer_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String number = rs.getString("number");
				
				Customer customer = new Customer(customerId, userId, name, address, number);
				return customer;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NotFoundException(userId, "customer");
	}

	@Override
	public boolean addCustomer(Customer customer) {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into customer values(null, ?,?,?,?)")) {
			
			pstmt.setString(1, customer.getUserId());
			pstmt.setString(2, customer.getName());
			pstmt.setString(3, customer.getAddress());
			pstmt.setString(4, customer.getNumber());
			
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
	public boolean deleteCustomerByUserId(String userId) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from customer where user_id = ?")) {
			
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
	public boolean updateCustomer(Customer customer) {
		final String statement = 
				"update customer "
				+ "set "
				+ "name = ? , "
				+ "address = ? , "
				+ "number = ? "
				+ "WHERE user_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
			
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getAddress());
			pstmt.setString(3, customer.getNumber());
			pstmt.setString(4, customer.getUserId());
			
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
