package com.bankingsystem.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingsystem.dao.EmployeeDao;
import com.bankingsystem.model.Employee;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDao employeeDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
    	this.employeeDao = new EmployeeDao();
      
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.doGet(request, response);
		
	}
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();

		
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				try {
					insertEmployee(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case "/delete":
				try {
					deleteEmployee(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case "/edit":
				try {
					showEditForm(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case "/update":
				try {
					updateEmployee(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			default:
				try {
					listEmployee(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		
		
	}
	
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		employeeDao.deleteEmployee(id);
		response.sendRedirect("list");

	}
	
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");

		Employee employee = new Employee(id, name, email, address, phone);
		employeeDao.updateEmployee(employee);
		response.sendRedirect("list");
	}
	
	
	private void listEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Employee> listEmployee = employeeDao.selectAllEmployees();
		request.setAttribute("listEmployee", listEmployee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingEmployee = employeeDao.selectEmployee(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		request.setAttribute("employee", existingEmployee);
		dispatcher.forward(request, response);

	}
	

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		Employee newEmployee = new Employee(name, email, address, phone);
		employeeDao.insertEmployee(newEmployee);
		response.sendRedirect("list");
	}

}
