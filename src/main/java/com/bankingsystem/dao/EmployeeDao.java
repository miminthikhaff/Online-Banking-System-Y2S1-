package com.bankingsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankingsystem.model.Employee;

public class EmployeeDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/bank_db?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO employees" + "  (name, email, address, phone) VALUES "
			+ " (?, ?, ?, ?);";

	private static final String SELECT_EMPLOYEES_BY_ID = "select id,name,email,address,phone from employees where id =?";
	private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
	private static final String DELETE_EMPLOYEES_SQL = "delete from employees where id = ?;";
	private static final String UPDATE_EMPLOYEES_SQL = "update employees set name = ?,email= ?, address =?,phone= ? where id = ?;";
	
	
	
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	//  Insert Employee Start..............//
	
	public void insertEmployee(Employee employee) throws SQLException {
		System.out.println(INSERT_EMPLOYEES_SQL);
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL)) {
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getEmail());
			preparedStatement.setString(3, employee.getAddress());
			preparedStatement.setString(4, employee.getPhone());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//  Insert Employee End..............//
	
	//  Update Employee Start..............//
	
	public boolean updateEmployee(Employee employee) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEES_SQL);) {
			statement.setString(1, employee.getName());
			statement.setString(2, employee.getEmail());
			statement.setString(3, employee.getAddress());
			statement.setString(4, employee.getPhone());
			statement.setInt(5, employee.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

//  Update Employee End..............//
	
	
	
//  Select Employee by id Start..............//
	
	
	public Employee selectEmployee(int id) {
		Employee employee = null;
		
		try (Connection connection = getConnection();
			
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEES_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();

			
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				employee = new Employee(id, name, email, address,phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	
//  Select Employee by id End..............//
	
	
//  Select All Employee  Start..............//
	
	
	public List<Employee> selectAllEmployees() {

		
		List<Employee> employees = new ArrayList<>();
	
		try (Connection connection = getConnection();

			
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);) {
			System.out.println(preparedStatement);
		
			ResultSet rs = preparedStatement.executeQuery();

		
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				employees.add(new Employee(id, name, email, address,phone));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	
//  Select All Employee  End..............//
	
	
	
//  Delete Employee  Start..............//
	
	
	
	public boolean deleteEmployee(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEES_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	
	
	
	
	
	
	
	
	
//  Delete Employee  End..............//
	
	
}
