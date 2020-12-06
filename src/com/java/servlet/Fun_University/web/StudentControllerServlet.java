package com.java.servlet.Fun_University.web;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.sql.*;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;

	@Resource(name = "jdbc/Fun_University")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		// Create our StudentDBUtil object and pass in connection pool
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Read the command parameter
			String theCommand = request.getParameter("command");

			// If command is missing return default list
			if (theCommand == null) {
				listStudents(request, response);
			}
			
			// SWITCH statements to perform operations.
			switch (theCommand) {
			case "LIST": // Same name from ListStudent.jsp
				listStudents(request, response);
				break;
			case "ADD": // Same name from AddStudentForm.jsp
				addStudent(request, response);
				break;
			case "LOAD": // Same name from ListStudent.jsp
				loadStudent(request, response);
				break;
			case "UPDATE": // Same name from UpdateStudentForm.jsp
				updateStudent(request, response);
				break;
			case "DELETE": // Same name ListStudent.jsp
				deleteStudent(request, response);
				break;
			default:
				listStudents(request, response);
			}
		} 
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	
	// Method to return all student data.
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		// Get students from StudentBDUtil.
		List<Student> students = studentDBUtil.getStudent();

		// Add students to request.
		request.setAttribute("StudentList", students);

		// Send to ListStudent.jsp(view).
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ListStudents.jsp");
		requestDispatcher.forward(request, response);
	}
	
	
	// Method to add student data.
	public void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Read data from "Form".
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Create new row.
		Student temp = new Student(firstName, lastName, email);

		// Add student.
		StudentDBUtil.addStudent(temp);
		
		// Return ListStudent.jsp(view).
		listStudents(request, response);
		
		
		
	}
	
	
	// Method to load student data while updating.
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read student id from Form Data.
		String theStudentId = request.getParameter("studentId");

		// Get student data from database (StudenDBUtil).
		Student theStudent = studentDBUtil.getStudent(theStudentId);

		// Place student in request attribute.
		request.setAttribute("TheStudent", theStudent);

		// Send to UpdateStudentForm.jsp page.
		RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdateStudentForm.jsp");
		dispatcher.forward(request, response);
	}

	
	// Method to update student data.
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Read student data from "Form".
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Create new Student Object.
		Student theStudent = new Student(id, firstName, lastName, email);
		
		//Perform update on database.
		studentDBUtil.updateStudent(theStudent);
		
		// Send updated data back to ListStudent.jsp
		listStudents(request, response);
	}
	
	
	// Method to delete student data.
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read student id from "Form".
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		
		// Delete student from database.
		studentDBUtil.deleteStudent(studentId);
		
		//Return to ListStudent.jsp.
		listStudents(request, response);
	}
}
